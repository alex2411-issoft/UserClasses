package com.yakubovskiy.design.service;

import com.yakubovskiy.design.domain.User;
import com.yakubovskiy.design.domain.UserStatus;
import com.yakubovskiy.design.exception.LogicException;
import com.yakubovskiy.design.exception.ResourceNotFoundException;
import com.yakubovskiy.design.persistence.UserStorage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkState;

@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(@NonNull User user) {
        Optional<User> optionalUser = userStorage.findByUsername(user.getUsername());
        checkState(optionalUser.isEmpty(), "username is busy");

        user.setStatus(UserStatus.ACTIVE);
        log.info("User {} was created", user);

        return userStorage.persist(user);
    }

    public User editUser(@NonNull UUID id, @NonNull User editUser) {
        User user = findUserById(id);

        user.setFirstName(editUser.getFirstName());
        user.setLastName(editUser.getLastName());
        user.setAge(editUser.getAge());

        log.info("User {} was edited", user);
        return userStorage.persist(user);
    }

    public User findUserById(@NonNull UUID id) {
        User user = userStorage.load(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        log.info("User {} was received", user);
        return user;
    }

    public List<User> findAllUsers() {
        log.info("List of users was received");
        return userStorage.loadAll();
    }

    public User blockUser(@NonNull UUID id) {
        User user = userStorage.load(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            log.error("Error while block user {}. This user has been already blocked.", user);
            throw new LogicException("User has been already blocked");
        }

        user.setStatus(UserStatus.BLOCKED);
        log.info("User {} has been blocked", user);
        return userStorage.persist(user);
    }

    public User unblockUser(@NonNull UUID id) {
        User user = userStorage.load(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (user.getStatus().equals(UserStatus.ACTIVE)) {
            log.error("Error while unblock user {}. This user has been already active.", user);
            throw new LogicException("User has been already active");
        }

        user.setStatus(UserStatus.ACTIVE);
        log.info("User {} has been unblocked", user);
        return userStorage.persist(user);
    }
}
