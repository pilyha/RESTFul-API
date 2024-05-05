package com.rest.user.service.impl;

import com.rest.common.exception.RestApiInvalidDataException;
import com.rest.user.model.User;
import com.rest.user.service.UserValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class UserServiceImplUnitTest {

    @Mock
    private UserValidationService userValidationService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_ValidUser() throws RestApiInvalidDataException {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.of(1990, 5, 15));

        setUsersFieldForService(new ArrayList<>(List.of(user)));

        Mockito.doNothing().when(userValidationService).validateUser(user);

        User createdUser = userService.createUser(user);

        Assertions.assertNotNull(createdUser.getId());
        Assertions.assertEquals("John", createdUser.getFirstName());
        Assertions.assertEquals("Doe", createdUser.getLastName());
        Assertions.assertEquals("john.doe@example.com", createdUser.getEmail());
        Assertions.assertEquals(LocalDate.of(1990, 5, 15), createdUser.getBirthDate());
    }

    @Test
    void testCreateUser_InvalidUser() throws RestApiInvalidDataException {
        setUsersFieldForService(new ArrayList<>());

        User user = new User();
        Mockito.doThrow(RestApiInvalidDataException.class).when(userValidationService).validateUser(user);
        Assertions.assertThrows(RestApiInvalidDataException.class, () -> userService.createUser(user));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        setUsersFieldForService(new ArrayList<>());
        Assertions.assertThrows(RestApiInvalidDataException.class, () -> userService.updateUser(1L, new User()));
    }

    @Test
    void testUpdateUser_ValidUser() throws RestApiInvalidDataException {
        User existingUser = new User();
        existingUser.setId(1L);

        setUsersFieldForService(new ArrayList<>(List.of(existingUser)));

        User updatedUser = new User();
        updatedUser.setFirstName("UpdatedFirstName");
        updatedUser.setLastName("UpdatedLastName");
        updatedUser.setEmail("updated.email@example.com");
        updatedUser.setBirthDate(LocalDate.of(2000, 1, 1));

        Mockito.doNothing().when(userValidationService).validateUser(updatedUser);

        User updated = userService.updateUser(1L, updatedUser);

        Assertions.assertEquals(updatedUser.getFirstName(), updated.getFirstName());
        Assertions.assertEquals(updatedUser.getLastName(), updated.getLastName());
        Assertions.assertEquals(updatedUser.getEmail(), updated.getEmail());
        Assertions.assertEquals(updatedUser.getBirthDate(), updated.getBirthDate());
    }

    @Test
    void testDeleteUser_UserNotFound() {
        setUsersFieldForService(new ArrayList<>());

        Assertions.assertThrows(RestApiInvalidDataException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void testDeleteUser_ValidUser() throws RestApiInvalidDataException {
        User user = new User();
        user.setId(1L);
        List<User> users = new ArrayList<>();
        users.add(user);

        setUsersFieldForService(users);

        userService.deleteUser(1L);

        Assertions.assertTrue(users.isEmpty());
    }

    @Test
    void testGetUsersByBirthDateRange_InvalidDateRange() {
        Assertions.assertThrows(RestApiInvalidDataException.class, () -> userService.getUsersByBirthDateRange("2024-01-01", "2023-12-31"));
    }

    @Test
    void testGetUsersByBirthDateRange_ValidDateRange() throws RestApiInvalidDataException {
        User user1 = new User();
        user1.setBirthDate(LocalDate.of(2000, 1, 1));
        User user2 = new User();
        user2.setBirthDate(LocalDate.of(2003, 2, 2));

        setUsersFieldForService(new ArrayList<>(List.of(user1, user2)));

        List<User> usersInRange = userService.getUsersByBirthDateRange("01-01-2000", "25-03-2003");

        Assertions.assertEquals(2, usersInRange.size());
    }

    private void setUsersFieldForService(List<User> users) {
        try {
            Field usersField = UserServiceImpl.class.getDeclaredField("users");
            usersField.setAccessible(true);
            usersField.set(userService, users);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}