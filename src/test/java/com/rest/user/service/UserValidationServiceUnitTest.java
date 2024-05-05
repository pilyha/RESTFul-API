package com.rest.user.service;

import com.rest.common.exception.RestApiInvalidDataException;
import com.rest.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UserValidationServiceUnitTest {
    private final UserValidationService userValidationService = new UserValidationService();

    @Test
    void testValidateUser_ValidUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.of(1990, 5, 15));
        Assertions.assertDoesNotThrow(() -> userValidationService.validateUser(user));
    }

    @Test
    void testValidateUser_NullUser() {
        Assertions.assertThrows(RestApiInvalidDataException.class, () -> userValidationService.validateUser(null));
    }

    @Test
    void testValidateUser_InvalidEmail() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe");
        user.setBirthDate(LocalDate.of(1990, 5, 15));
        Assertions.assertThrows(RestApiInvalidDataException.class, () -> userValidationService.validateUser(user));
    }

    @Test
    void testValidateUser_InvalidFirstName() {
        User user = new User();
        user.setFirstName("");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.of(1990, 5, 15));
        Assertions.assertThrows(RestApiInvalidDataException.class, () -> userValidationService.validateUser(user));
    }

    @Test
    void testValidateUser_InvalidLastName() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.of(1990, 5, 15));
        Assertions.assertThrows(RestApiInvalidDataException.class, () -> userValidationService.validateUser(user));
    }

    @Test
    void testValidateUser_InvalidBirthDate() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.now());
        Assertions.assertThrows(RestApiInvalidDataException.class, () -> userValidationService.validateUser(user));
    }

    @Test
    void testValidateDateRanges_ValidDateRange() {
        LocalDate fromDate = LocalDate.of(2022, 1, 1);
        LocalDate toDate = LocalDate.of(2022, 12, 31);
        Assertions.assertDoesNotThrow(() -> userValidationService.validateDateRanges(fromDate, toDate));
    }

    @Test
    void testValidateDateRanges_InvalidDateRange() {
        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2022, 12, 31);
        Assertions.assertThrows(RestApiInvalidDataException.class, () -> userValidationService.validateDateRanges(fromDate, toDate));
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        var field = UserValidationService.class.getDeclaredField("USER_MIN_AGE");
        field.setAccessible(true);
        field.set(userValidationService, 18);
    }
}