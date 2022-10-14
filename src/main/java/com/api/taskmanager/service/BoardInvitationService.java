package com.api.taskmanager.service;

import com.api.taskmanager.Helper;
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

import static com.api.taskmanager.exception.TaskManagerCustomException.FORBIDDEN;

@Service
public class BoardInvitationService {

    private BoardService boardService;
    private UserService userService;
    private RabbitMqService rabbitMqService;
    private BoardInvitationRepository boardInvitationRepository;

    @Autowired
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

    public void acceptInvitation(UUID invitationId) {
        Optional<BoardInvitation> optionalBoardInvitation = boardInvitationRepository.findById(invitationId);

        if(optionalBoardInvitation.isPresent()) {
            BoardInvitation boardInvitation = optionalBoardInvitation.get();
            boardInvitation.setInvitationStatus(InvitationStatus.ACCEPTED);

            boardInvitationRepository.save(boardInvitation);
            boardService.newMemberByInvitation(boardInvitation);
        }
    }

    private EmailDto createInvitationEmail(User user, String boardName, UUID boardIdInvitation) {
        EmailDto emailDto = EmailDto.builder()
                .emailTo(user.getEmail())
                .subject(Helper.EMAIL_SUBJECT)
                .text(Helper.EMAIL_BODY
                        .replace("[BOARD_NAME]", boardName)
                        .replace("[LINK_URL]",
                                "www.localhost:5000/board-invitation/accept-invite/"+boardIdInvitation))
                .build();
        return emailDto;
    }

    private boolean hasAccess(BoardDtoResponse board, Principal principal) {
        return (board.getMemberList().stream().filter((member) -> (member.getUsername().equals(principal.getName())))
                .count() > 0 || board.getOwner().getUsername().equals(principal.getName()));
    }
}
