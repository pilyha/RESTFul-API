package com.rest.user.service;

import com.rest.common.exception.RestApiInvalidDataException;
import com.rest.user.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user) throws RestApiInvalidDataException;

    User updateUser(Long id, User user) throws RestApiInvalidDataException;

    User updateUserFields(Long id, User user) throws RestApiInvalidDataException;

    void deleteUser(Long id) throws RestApiInvalidDataException;

    List<User> getUsersByBirthDateRange(String fromDate, String toDate) throws RestApiInvalidDataException;
}
