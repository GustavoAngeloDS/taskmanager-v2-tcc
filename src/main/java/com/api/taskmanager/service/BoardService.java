package com.api.taskmanager.service;

import com.api.taskmanager.exception.TaskManagerCustomException;
import com.api.taskmanager.model.Board;
import com.api.taskmanager.model.BoardInvitation;
import com.api.taskmanager.model.User;
import com.api.taskmanager.repository.BoardRepository;
import com.api.taskmanager.response.BoardDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.api.taskmanager.exception.TaskManagerCustomException.FORBIDDEN;
import static com.api.taskmanager.exception.TaskManagerCustomException.ID_NOT_FOUND;

@Service
public class BoardService {

    private BoardRepository repository;
    private UserService userService;

    @Autowired
    BoardService(BoardRepository boardRepository, UserService userService) {
        this.repository = boardRepository;
        this.userService = userService;
    }

    public List<BoardDtoResponse> findAll(Principal principal) {
        List<BoardDtoResponse> boardDtoResponseList = new ArrayList<>();

        repository.findAllBoardsByUsername(principal.getName()).forEach(board -> {
            boardDtoResponseList.add(BoardDtoResponse.fromEntity(board));
        });
        return boardDtoResponseList;
    }

    public void newMemberByInvitation(BoardInvitation boardInvitation) {
        User user = userService.findByEmail(boardInvitation.getMemberEmail()).get();
        Optional<Board> board = repository.findById(boardInvitation.getBoardId()) ;
        if(board.isPresent()) {
            board.get().getMemberList().add(user);
            repository.save(board.get());
        }
    }

    public BoardDtoResponse removeBoardMember(UUID boardId, String memberEmail, Principal principal) {
        Board board = repository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!isOwner(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        User user = userService.findByEmail(memberEmail).orElseThrow(() -> new TaskManagerCustomException((ID_NOT_FOUND)));

        if(board.getOwner().getId() != user.getId())
            board.getMemberList().remove(user);
        return BoardDtoResponse.fromEntity(repository.save(board));
    }

    public BoardDtoResponse findById(UUID id, Principal principal) {
        Board board = repository.findById(id).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        return BoardDtoResponse.fromEntity(board);
    }

    public BoardDtoResponse create(Board board, Principal principal) {
        User owner = userService.findByUsername(principal.getName());

        board.setOwner(owner);
        board.getMemberList().add(owner);

        Board createdBoard = repository.save(board);
        return BoardDtoResponse.fromEntity(createdBoard);
    }

    public BoardDtoResponse update(UUID boardId, Board newBoardData, Principal principal) {
        Board board = repository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        board.setDescription(newBoardData.getDescription());
        board.setName(newBoardData.getName());

        Board updatedBoard = repository.save(board);
        return BoardDtoResponse.fromEntity(updatedBoard);
    }

    public void remove(UUID boardId, Principal principal) {
        Board board = repository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        repository.delete(board);
    }

    private boolean hasAccess(Board board, Principal principal) {
        return (board.getMemberList().stream().filter((member) -> (member.getUsername().equals(principal.getName())))
                .count() > 0 || board.getOwner().getUsername().equals(principal.getName()));
    }

    private boolean isOwner(Board board, Principal principal) {
        return board.getOwner().getUsername().equals(principal.getName());
    }
}
