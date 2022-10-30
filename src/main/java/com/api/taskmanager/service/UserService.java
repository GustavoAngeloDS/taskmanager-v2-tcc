package com.api.taskmanager.service;

import com.api.taskmanager.constants.DefaultValues;
import com.api.taskmanager.constants.Helper;
import com.api.taskmanager.dto.EmailDto;
import com.api.taskmanager.enums.PasswordResetStatus;
import com.api.taskmanager.exception.TaskManagerCustomException;
import com.api.taskmanager.model.NewPassword;
import com.api.taskmanager.model.PasswordResetRequest;
import com.api.taskmanager.model.User;
import com.api.taskmanager.repository.PasswordResetRequestRepository;
import com.api.taskmanager.repository.UserRepository;
import com.api.taskmanager.response.UserDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

import static com.api.taskmanager.exception.TaskManagerCustomException.*;

@Service
public class UserService extends ObjectAuthorizationAbstractService implements UserDetailsService{

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private PasswordResetRequestRepository passwordResetRequestRepository;
    private RabbitMqService rabbitMqService;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                PasswordResetRequestRepository passwordResetRequestRepository, RabbitMqService rabbitMqService) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetRequestRepository = passwordResetRequestRepository;
        this.rabbitMqService = rabbitMqService;
    }

    public UserDtoResponse authenticate(String username) {
        User user = repository.findByUsername(username).orElseThrow(() -> new TaskManagerCustomException(INVALID_CREDENTIALS));
        return UserDtoResponse.fromEntity(user);
    }

    public UserDtoResponse findById(UUID id, Principal principal) {
        User user = repository.findById(id).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(user, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        return new UserDtoResponse(user.getId(), user.getEmail(), user.getUsername(),
                user.getNickName(), user.getPhoneNumber());
    }

    public UserDtoResponse create(User user) {
        consistInputData(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User createdUser = repository.save(user);
        return new UserDtoResponse(createdUser.getId(), createdUser.getEmail(), createdUser.getUsername(),
                createdUser.getNickName(), createdUser.getPhoneNumber());
    }

    public void createPasswordResetRequest(PasswordResetRequest passwordResetRequest) {
        if(isEligibleToResetPassword(passwordResetRequest)){
            inactivateOlderResetRequest(passwordResetRequest.getUserEmail());

            PasswordResetRequest resetRequest = passwordResetRequestRepository
                    .save(new PasswordResetRequest(passwordResetRequest.getUserEmail()));

            rabbitMqService.sendMessage(buildPasswordResetRequestMessage(resetRequest.getId(),
                    resetRequest.getUserEmail()));
        }
    }

    private EmailDto buildPasswordResetRequestMessage(UUID passwordResetRequestId, String userEmail) {
        return EmailDto.builder()
                .emailTo(userEmail)
                .text(String.format(Helper.PASSWORD_RESET_EMAIL_BODY,
                        DefaultValues.RESET_PASSWORD_FRONTEND_URL+passwordResetRequestId))
                .subject(Helper.PASSWORD_RESET_EMAIL_SUBJECT)
                .build();
    }

    private void inactivateOlderResetRequest(String userEmail) {
        passwordResetRequestRepository.findByUserEmail(userEmail).stream()
                .filter(request -> PasswordResetStatus.ACTIVE.equals(request.getStatus()))
                .forEach(request -> {
                    request.setStatus(PasswordResetStatus.INACTIVE);
                    passwordResetRequestRepository.save(request);
                });
    }

    private boolean isEligibleToResetPassword(PasswordResetRequest passwordResetRequest) {
        return repository.findByEmail(passwordResetRequest.getUserEmail()).isPresent();
    }

    public void updatePassword(UUID passwordResetRequestId, NewPassword newPassword) {
        PasswordResetRequest resetRequest = passwordResetRequestRepository.findById(passwordResetRequestId)
                .orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        if(PasswordResetStatus.INACTIVE.equals(resetRequest.getStatus()))
            throw new TaskManagerCustomException(PASSWORD_REQUEST_INVALID);

        User userToUpdate = repository.findByEmail(resetRequest.getUserEmail()).get();
        userToUpdate.setPassword(passwordEncoder.encode(newPassword.getPassword()));
        repository.save(userToUpdate);

        resetRequest.setStatus(PasswordResetStatus.INACTIVE);
        passwordResetRequestRepository.save(resetRequest);
    }

    public UserDtoResponse update(UUID id, User newUserData, Principal principal) {
        User user = repository.findById(id).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(user, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        user.setNickName(newUserData.getNickName());
        user.setPhoneNumber(newUserData.getPhoneNumber());

        User updatedUser = repository.save(user);
        return new UserDtoResponse(updatedUser.getId(), updatedUser.getEmail(), updatedUser.getUsername(),
                updatedUser.getNickName(), updatedUser.getPhoneNumber());
    }

    public void remove(UUID id, Principal principal) {
        User user = repository.findById(id).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(user, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        repository.delete(user);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username).get();
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    private void consistInputData(User user) {
        if(repository.findByUsername(user.getUsername()).isPresent())
            throw new TaskManagerCustomException(USERNAME_NOT_AVAILABLE);
        if(repository.findByEmail(user.getEmail()).isPresent())
            throw new TaskManagerCustomException(USER_ALREADY_USED_EMAIL);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }
}
