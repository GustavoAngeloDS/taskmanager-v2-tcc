package com.api.taskmanager.service;

import com.api.taskmanager.constants.Helper;
import com.api.taskmanager.dto.EmailDto;
import com.api.taskmanager.enums.InvitationStatus;
import com.api.taskmanager.exception.TaskManagerCustomException;
import com.api.taskmanager.model.BoardInvitation;
import com.api.taskmanager.model.User;
import com.api.taskmanager.repository.BoardInvitationRepository;
import com.api.taskmanager.response.BoardDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import static com.api.taskmanager.constants.DefaultValues.ACCEPT_INVITE_FRONTEND_URL;
import static com.api.taskmanager.exception.TaskManagerCustomException.FORBIDDEN;

@Service
public class BoardInvitationService extends ObjectAuthorizationAbstractService {

    private BoardService boardService;
    private UserService userService;
    private RabbitMqService rabbitMqService;
    private BoardInvitationRepository boardInvitationRepository;

    BoardInvitationService(BoardService boardService, UserService userService,
                           BoardInvitationRepository boardInvitationRepository, RabbitMqService rabbitMqService) {
        this.boardService = boardService;
        this.userService = userService;
        this.boardInvitationRepository = boardInvitationRepository;
        this.rabbitMqService = rabbitMqService;
    }

    public BoardInvitation newBoardInvitation(BoardInvitation boardInvitation, Principal principal) {
        BoardDtoResponse boardDtoResponse = boardService.findById(boardInvitation.getBoardId(), principal);
        if(!hasAccess(boardDtoResponse, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        Optional<User> userToInvite = userService.findByEmail(boardInvitation.getMemberEmail());
        if(userToInvite.isEmpty()) {
            return new BoardInvitation();
        }

        BoardInvitation bi = boardInvitationRepository.save(boardInvitation);
        rabbitMqService.sendMessage(createInvitationEmail(userToInvite.get(), boardDtoResponse.getName(), bi.getId()));

        return bi;
    }

    public BoardInvitation acceptInvitation(UUID invitationId) {
        Optional<BoardInvitation> optionalBoardInvitation = boardInvitationRepository.findById(invitationId);

        if(optionalBoardInvitation.isPresent() && optionalBoardInvitation.get()
                .getInvitationStatus() == InvitationStatus.WAITING) {
            BoardInvitation boardInvitation = optionalBoardInvitation.get();
            boardInvitation.setInvitationStatus(InvitationStatus.ACCEPTED);

            boardInvitationRepository.save(boardInvitation);
            boardService.newMemberByInvitation(boardInvitation);
            return boardInvitation;
        } else {
            return null;
        }
    }

    private EmailDto createInvitationEmail(User user, String boardName, UUID boardIdInvitation) {
        EmailDto emailDto = EmailDto.builder()
                .emailTo(user.getEmail())
                .subject(String.format(Helper.NEW_INVITATION_EMAIL_SUBJECT, boardName))
                .text(String.format(Helper.NEW_INVITATION_EMAIL_BODY, boardName, ACCEPT_INVITE_FRONTEND_URL+boardIdInvitation))
                .build();
        return emailDto;
    }
}
