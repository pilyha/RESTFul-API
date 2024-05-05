package com.rest.user.service.impl;

import com.rest.common.exception.RestApiInvalidDataException;
import com.rest.common.util.DateConverter;
import com.rest.common.util.MessageConstants;
import com.rest.user.model.User;
import com.rest.user.service.UserService;
import com.rest.user.service.UserValidationService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserValidationService userValidationService;

    private final List<User> users;

    private final AtomicInteger id = new AtomicInteger(0);

    @Override
    public User createUser(User user) throws RestApiInvalidDataException {
        userValidationService.validateUser(user);
        user.setId((long) id.incrementAndGet());
        users.add(user);
        return user;
    }

    @Override
    public User updateUser(Long id, User updatedUser) throws RestApiInvalidDataException {
        var data = users.stream().filter(it -> it.getId().equals(id)).findFirst();
        if (data.isPresent()) {
            var user = data.get();
            userValidationService.validateUser(updatedUser);
            var userIndex = users.indexOf(user);
            BeanUtils.copyProperties(updatedUser, user);
            users.set(userIndex, user);
            return user;
        } else {
            throw new RestApiInvalidDataException(String.format(MessageConstants.USER_NOT_FOUND, id));
        }
    }

    @Override
    public User updateUserFields(Long id, User updatedUser) throws RestApiInvalidDataException {
        var data = users.stream().filter(it -> it.getId().equals(id)).findFirst();
        if (data.isPresent()) {
            var user = data.get();
            userValidationService.validateUser(updatedUser);
            var userIndex = users.indexOf(user);
            updateUserFields(updatedUser, user);
            users.set(userIndex, user);
            return user;
        } else {
            throw new RestApiInvalidDataException(String.format(MessageConstants.USER_NOT_FOUND, id));
        }
    }

    @Override
    public void deleteUser(Long userId) throws RestApiInvalidDataException {
        var data = users.stream().filter(it -> it.getId().equals(userId)).findFirst();
        if (data.isPresent()) {
            users.removeIf(it -> it.getId().equals(userId));
        } else {
            throw new RestApiInvalidDataException(String.format(MessageConstants.USER_NOT_FOUND, userId));
        }
    }

    @Override
    public List<User> getUsersByBirthDateRange(String fromDate, String toDate) throws RestApiInvalidDataException {
        var formattedFromDate = DateConverter.parseDate(fromDate);
        var formattedToDate = DateConverter.parseDate(toDate);
        userValidationService.validateDateRanges(formattedFromDate, formattedToDate);
        return users.stream()
                .filter(user -> (user.getBirthDate().isEqual(formattedFromDate) || user.getBirthDate().isAfter(formattedFromDate))
                        && (user.getBirthDate().isEqual(formattedToDate) || user.getBirthDate().isBefore(formattedToDate)))
                .toList();
    }

    private void updateUserFields(User source, User target) {
        target.setEmail(source.getEmail());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setBirthDate(source.getBirthDate());
        target.setAddress(StringUtils.isNotBlank(source.getAddress()) ? source.getAddress() : target.getAddress());
        target.setPhoneNumber(StringUtils.isNotBlank(source.getPhoneNumber()) ? source.getPhoneNumber() : target.getPhoneNumber());
    }
}
