package com.api.taskmanager.service;

import com.api.taskmanager.exception.TaskManagerCustomException;
import com.api.taskmanager.model.*;
import com.api.taskmanager.model.Stack;
import com.api.taskmanager.repository.*;
import com.api.taskmanager.response.TaskDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static com.api.taskmanager.exception.TaskManagerCustomException.*;

@Service
public class TaskService extends ObjectAuthorizationAbstractService {

    private TaskRepository taskRepository;
    private BoardRepository boardRepository;
    private StackRepository stackRepository;
    private UserRepository userRepository;
    private InternalTaskRepository internalTaskRepository;
    private TagRepository tagRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, BoardRepository boardRepository, StackRepository stackRepository,
            UserRepository userRepository, InternalTaskRepository internalTaskRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
        this.stackRepository = stackRepository;
        this.userRepository = userRepository;
        this.internalTaskRepository = internalTaskRepository;
        this.tagRepository = tagRepository;
    }

    public void removeTagFromTasks(Tag tag, Principal principal) {
        if(!hasAccess(tag, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        taskRepository.findAllByBoardId(tag.getBoard().getId()).stream().filter(task -> task.getTag() == tag)
                .forEach(taskToRemoveTag -> {
                    taskToRemoveTag.setTag(null);
                    taskRepository.save(taskToRemoveTag);
                });
    }

    public List<TaskDtoResponse> findAllByBoardId(UUID boardId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        List<TaskDtoResponse> taskDtoResponseList = taskRepository.findAllByBoardId(boardId).stream()
                .map(TaskDtoResponse::fromEntity).collect(Collectors.toList());

        return taskDtoResponseList;
    }

    public TaskDtoResponse findById(UUID boardId, UUID taskId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        return TaskDtoResponse.fromEntity(task);
    }

    public TaskDtoResponse create(UUID boardId, UUID stackId, Task task, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Stack stack = stackRepository.findById(stackId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        task.setStack(stack);
        task.setPosition(taskRepository.findNextAvailablePositionByBoardAndStack(boardId, stackId));

        task.setNotificationConfiguration(new NotificationConfiguration());

        task.setDeliveryDate(new DeliveryDate());

        Task createdTask = taskRepository.save(task);
        return TaskDtoResponse.fromEntity(createdTask);
    }

    public TaskDtoResponse updateTaskTag(UUID boardId, UUID taskId, UUID tagId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        tag.getTaskList().add(task);
        task.setTag(tag);

        tagRepository.save(tag);
        return TaskDtoResponse.fromEntity(taskRepository.save(task));
    }

    public TaskDtoResponse update(UUID boardId, UUID taskId, Task newTaskData, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        task.setDescription(newTaskData.getDescription());
        task.setTitle(newTaskData.getTitle());

        Task updatedTask = taskRepository.save(task);
        return TaskDtoResponse.fromEntity(updatedTask);
    }

    public TaskDtoResponse updateTaskStack(UUID boardId, UUID taskId, UUID newStackId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Stack stack = stackRepository.findById(newStackId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        task.setStack(stack);

        Task updatedTask = taskRepository.save(task);
        return TaskDtoResponse.fromEntity(updatedTask);
    }

    public TaskDtoResponse updateTaskMembers(UUID boardId, UUID taskId, List<User> taskMembers, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        task.getMemberList().removeIf((member) -> !taskMembers.contains(member));

        taskMembers.forEach(member -> {
            User newMember = userRepository.findById(member.getId()).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
            task.getMemberList().add(newMember);
        });

        Task updatedTask = taskRepository.save(task);
        return TaskDtoResponse.fromEntity(updatedTask);
    }

    public void removeTaskMember(UUID boardId, UUID taskId, UUID memberId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        User member = userRepository.findById(memberId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        if(task.getMemberList().contains(member)) task.getMemberList().remove(member);
        else throw new TaskManagerCustomException(USER_IS_NOT_MEMBER);

        taskRepository.save(task);
    }

    public void remove(UUID boardId, UUID taskId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        taskRepository.delete(task);
    }

    public TaskDtoResponse includeInternalTask(UUID boardId, UUID taskId, InternalTask internalTask, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        InternalTask newInternalTask = new InternalTask(internalTask.getChecked(), internalTask.getDescription(),
                internalTaskRepository.findNextAvailablePositionByTask(taskId), task);
        internalTaskRepository.save(newInternalTask);
        task.getInternalTasks().add(newInternalTask);

        return TaskDtoResponse.fromEntity(taskRepository.save(task));
    }

    public TaskDtoResponse updateInternalTask(UUID boardId, UUID taskId, UUID internalTaskId,
                                                       InternalTask newInternalTaskData, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        InternalTask internalTask = internalTaskRepository.findById(internalTaskId)
                .orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        internalTask.setChecked(newInternalTaskData.getChecked());
        internalTask.setDescription(newInternalTaskData.getDescription());
        task.getInternalTasks().add(internalTask);

        return TaskDtoResponse.fromEntity(taskRepository.save(task));
    }

    public TaskDtoResponse updateInternalTaskPosition(UUID boardId, UUID taskId, UUID internalTaskId,
                                                      Integer newPosition, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        InternalTask internalTask = internalTaskRepository.findById(internalTaskId)
                .orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        if(newPosition >= 0 && newPosition < task.getInternalTasks().size()) {
            InternalTask internalTaskAtNewPosition = task.getInternalTasks().stream()
                    .filter((internalTask1 -> internalTask1.getPosition() == newPosition))
                    .findFirst().get();

            if(!Objects.isNull(internalTaskAtNewPosition)) {
                internalTaskAtNewPosition.setPosition(internalTask.getPosition());
                task.getInternalTasks().add(internalTaskAtNewPosition);
            }

            internalTask.setPosition(newPosition);
            task.getInternalTasks().add(internalTask);
        }

        return TaskDtoResponse.fromEntity(taskRepository.save(task));
    }

    public void removeInternalTask(UUID boardId, UUID taskId, UUID internalTaskId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        InternalTask internalTask = internalTaskRepository.findById(internalTaskId)
                .orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        task.getInternalTasks().stream()
                .filter(filteredInternalTask -> filteredInternalTask.getPosition() > internalTask.getPosition())
                .forEach(internalTaskToUpdate -> {
                    internalTaskToUpdate.setPosition(internalTaskToUpdate.getPosition() - 1);
                    internalTaskRepository.save(internalTaskToUpdate);
                });

        task.getInternalTasks().remove(internalTask);
        internalTaskRepository.delete(internalTask);
        taskRepository.save(task);
    }

    public TaskDtoResponse updateTaskDeliveryDate(UUID boardId, UUID taskId, DeliveryDate deliveryDate, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        task.getDeliveryDate().setDate(deliveryDate.getDate());
        task.getDeliveryDate().setActive(deliveryDate.getActive());
        task.getDeliveryDate().setAccomplished(deliveryDate.getAccomplished());
        task.getDeliveryDate().setTime(deliveryDate.getTime());

        return TaskDtoResponse.fromEntity(taskRepository.save(task));
    }

    public TaskDtoResponse updateTaskNotificationConfiguration(UUID boardId, UUID taskId, NotificationConfiguration
            notificationConfiguration, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        task.getNotificationConfiguration().setNotificationType(notificationConfiguration.getNotificationType());
        task.getNotificationConfiguration().setMessage(notificationConfiguration.getMessage());
        task.getNotificationConfiguration().setTitle(notificationConfiguration.getTitle());

        return TaskDtoResponse.fromEntity(taskRepository.save(task));
    }

    public List<TaskDtoResponse> updateTaskPosition(UUID boardId, UUID newStackId, UUID taskId, Integer newPosition,
                                                    Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        Stack stack = stackRepository.findById(newStackId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        if(newPosition != task.getPosition() || !newStackId.equals(task.getStack().getId())) {
            if(newStackId.equals(task.getStack().getId())) {
                if(newPosition > task.getPosition()) {
                    taskRepository.findAllByBoardIdAndStackId(boardId, stack.getId()).stream()
                            .filter(filteredTask -> filteredTask.getPosition() > task.getPosition()
                                    && filteredTask.getPosition() != task.getPosition()
                                    && filteredTask.getPosition() <= newPosition)
                            .forEach(taskToUpdate -> {
                                taskToUpdate.setPosition(taskToUpdate.getPosition() - 1);
                                taskRepository.save(taskToUpdate);
                            });
                } else {
                    taskRepository.findAllByBoardIdAndStackId(boardId, stack.getId()).stream()
                            .filter(filteredTask -> filteredTask.getPosition() < task.getPosition()
                                    && filteredTask.getPosition() != task.getPosition()
                                    && filteredTask.getPosition() >= newPosition)
                            .forEach(taskToUpdate -> {
                                taskToUpdate.setPosition(taskToUpdate.getPosition() + 1);
                                taskRepository.save(taskToUpdate);
                            });
                }
                task.setPosition(newPosition);
                taskRepository.save(task);
            } else {
                taskRepository.findAllByBoardIdAndStackId(boardId, task.getStack().getId()).stream()
                        .filter(filteredTask -> filteredTask.getPosition() > task.getPosition())
                        .forEach(taskToUpdate -> {
                            taskToUpdate.setPosition(taskToUpdate.getPosition() - 1);
                        });
                taskRepository.findAllByBoardIdAndStackId(boardId, newStackId).stream()
                        .filter(filteredStack -> filteredStack.getPosition() >= newPosition)
                        .forEach(taskToUpdate -> {
                            taskToUpdate.setPosition(taskToUpdate.getPosition() + 1);
                            taskRepository.save(taskToUpdate);
                        });
                task.setStack(stack);
                task.setPosition(newPosition);
                taskRepository.save(task);
            }
        }

        List<TaskDtoResponse> taskDtoResponseList = taskRepository.findAllByBoardId(boardId).stream()
                .map(TaskDtoResponse::fromEntity).collect(Collectors.toList());

        Collections.sort(taskDtoResponseList, Comparator.comparing(TaskDtoResponse::getPosition));
        return taskDtoResponseList;
    }
}
