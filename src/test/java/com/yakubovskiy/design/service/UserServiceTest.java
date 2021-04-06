package com.yakubovskiy.design.service;

import com.yakubovskiy.design.domain.User;
import com.yakubovskiy.design.domain.UserStatus;
import com.yakubovskiy.design.exception.LogicException;
import com.yakubovskiy.design.exception.ResourceNotFoundException;
import com.yakubovskiy.design.persistence.UserStorage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private UserService userService;
    private final User user;

    @Mock
    private UserStorage userStorage;

    UserServiceTest() {
        user = User.of("ivan2411", "Ivan", "Ivanov");
    }

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userStorage);

        when(userStorage.persist(any())).thenReturn(user);
    }

    @Test
    void createUser() {
        when(userStorage.findByUsername(any())).thenReturn(Optional.empty());
        assertNotNull(userService.createUser(user));
    }

    @Test
    @SneakyThrows
    void editUser_withNewData() {
        when(userStorage.load(any())).thenReturn(Optional.of(user));
        User editUser = User.of("ivan2411", "Alex", "Yakubovskiy");

        User firstUser = (User) user.clone();
        User newUser = userService.editUser(user.getId(), editUser);

        assertNotNull(newUser);
        assertThat(firstUser, is(not(newUser)));
    }

    @Test
    @SneakyThrows
    void editUser_withOldData() {
        when(userStorage.load(any())).thenReturn(Optional.of(user));
        User editUser = User.of("ivan2411", "Ivan", "Ivanov");

        User firstUser = (User) user.clone();
        User newUser = userService.editUser(user.getId(), editUser);

        assertNotNull(newUser);
        assertThat(firstUser, is(newUser));
    }

    @Test
    void editUser_invalid() {
        when(userStorage.load(any())).thenReturn(Optional.empty());
        User editUser = User.of("ivan2411", "Ivan", "Ivanov");
        assertThrows(ResourceNotFoundException.class, () -> userService.editUser(user.getId(), editUser));
    }


    @Test
    void findUserById_success() {
        when(userStorage.load(any())).thenReturn(Optional.of(user));
        assertNotNull(userService.findUserById(UUID.randomUUID()));
    }

    @Test
    void findUserById_invalid() {
        when(userStorage.load(any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(UUID.randomUUID()));
    }

    @Test
    void findAllUsers_success() {
        when(userStorage.loadAll()).thenReturn(List.of(user));
        List<User> users = userService.findAllUsers();
        assertNotNull(users);
    }

    @Test
    void blockUser_success() {
        user.setStatus(UserStatus.ACTIVE);
        when(userStorage.load(any())).thenReturn(Optional.of(user));
        UserStatus status = userService.blockUser(UUID.randomUUID()).getStatus();
        assertThat(status, is(UserStatus.BLOCKED));
    }

    @Test
    void unblockUser_success() {
        user.setStatus(UserStatus.BLOCKED);
        when(userStorage.load(any())).thenReturn(Optional.of(user));
        UserStatus status = userService.unblockUser(UUID.randomUUID()).getStatus();
        assertThat(status, is(UserStatus.ACTIVE));
    }

    @Test
    void blockUser_invalid() {
        user.setStatus(UserStatus.BLOCKED);
        when(userStorage.load(any())).thenReturn(Optional.of(user));
        assertThrows(LogicException.class, () -> userService.blockUser(UUID.randomUUID()));
    }

    @Test
    void unblockUser_invalid() {
        user.setStatus(UserStatus.ACTIVE);
        when(userStorage.load(any())).thenReturn(Optional.of(user));
        assertThrows(LogicException.class, () -> userService.unblockUser(UUID.randomUUID()));
    }

}