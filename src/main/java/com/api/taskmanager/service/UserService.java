package com.api.taskmanager.service;

import com.api.taskmanager.model.User;
import com.api.taskmanager.repository.UserRepository;
import com.api.taskmanager.response.UserDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public List<UserDtoResponse> findAll() {
        List<UserDtoResponse> userDtoResponseList = new ArrayList<>();
        repository.findAll().forEach(user -> userDtoResponseList.add(
                new UserDtoResponse(user.getId(), user.getEmail(), user.getUsername(), user.getNickName(), user.getPhoneNumber()))
        );
        return userDtoResponseList;
    }

    public UserDtoResponse findById(Long id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent())
            return new UserDtoResponse();

        return new UserDtoResponse(user.get().getId(), user.get().getEmail(), user.get().getUsername(), user.get().getNickName(), user.get().getPhoneNumber());
    }

    public UserDtoResponse create(User user) {
        User createdUser = repository.save(user);
        return new UserDtoResponse(createdUser.getId(), createdUser.getEmail(), createdUser.getUsername(), createdUser.getNickName(), createdUser.getPhoneNumber());
    }

    public UserDtoResponse update(User updatedUser) {
        User savedUser = repository.save(updatedUser);
        return new UserDtoResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getUsername(), savedUser.getNickName(), savedUser.getPhoneNumber());
    }

    public void remove(User user) {
        repository.delete(user);
    }
}
