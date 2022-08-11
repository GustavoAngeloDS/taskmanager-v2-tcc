package com.api.taskmanager.service;

import com.api.taskmanager.enums.RoleName;
import com.api.taskmanager.exception.TaskManagerCustomException;
import com.api.taskmanager.model.Role;
import com.api.taskmanager.model.User;
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
public class UserService implements UserDetailsService {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDtoResponse> findAll() {
        List<UserDtoResponse> userDtoResponseList = new ArrayList<>();
        repository.findAll().forEach(user -> userDtoResponseList.add (
                new UserDtoResponse(user.getId(), user.getEmail(), user.getUsername(), user.getNickName(),
                        user.getPhoneNumber()))
        );
        return userDtoResponseList;
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
        user.setRoles(Arrays.asList(new Role(1L, RoleName.ROLE_USER)));

        User createdUser = repository.save(user);
        return new UserDtoResponse(createdUser.getId(), createdUser.getEmail(), createdUser.getUsername(),
                createdUser.getNickName(), createdUser.getPhoneNumber());
    }

    public UserDtoResponse update(UUID id, User newUserData, Principal principal) {
        User user = repository.findById(id).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(user, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        user.setNickName(newUserData.getNickName());
        user.setPhoneNumber(newUserData.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(newUserData.getPassword()));

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

    private boolean hasAccess(User user, Principal principal) {
        return principal.getName().equals(user.getUsername());
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
