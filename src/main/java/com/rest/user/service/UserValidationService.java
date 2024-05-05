package com.rest.user.service;

import com.rest.common.exception.RestApiInvalidDataException;
import com.rest.common.util.MessageConstants;
import com.rest.user.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class UserValidationService {

    @Value("${user.age.min}")
    private int USER_MIN_AGE; 
    
    public void validateUser(User user) throws RestApiInvalidDataException {
        if (user != null) {
            validateEmail(user.getEmail());
            validateBirthDate(user.getBirthDate());
            validateUserFirstAndLastName(user.getFirstName(), user.getLastName());
        } else {
            throw new RestApiInvalidDataException(MessageConstants.USER_REQUIRED);
        }
    }

    public void validateUserFirstAndLastName(String firstName, String lastName) throws RestApiInvalidDataException {
        if (StringUtils.isBlank(firstName)) {
            throw new RestApiInvalidDataException(MessageConstants.FIRST_NAME_REQUIRED);
        } else if (StringUtils.isBlank(lastName)) {
            throw new RestApiInvalidDataException(MessageConstants.LAST_NAME_REQUIRED);
        }
    }

    public void validateBirthDate(LocalDate userBirthDate) throws RestApiInvalidDataException {
        if (userBirthDate == null) {
            throw new RestApiInvalidDataException(MessageConstants.BIRTH_DATE_REQUIRED);
        }

        var minimumBirthDate = LocalDate.now().minusYears(USER_MIN_AGE);
        if (userBirthDate.isAfter(minimumBirthDate)) {
            throw new RestApiInvalidDataException(String.format(MessageConstants.USER_YOUNGER, USER_MIN_AGE));
        }
    }

    public void validateEmail(String email) throws RestApiInvalidDataException {
        if (StringUtils.isBlank(email)) {
            throw new RestApiInvalidDataException(MessageConstants.EMAIL_REQUIRED);
        }

        var emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        var pattern = Pattern.compile(emailPattern);
        var matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new RestApiInvalidDataException(MessageConstants.EMAIL_INVALID);
        }
    }

    public void validateDateRanges(LocalDate from, LocalDate to) throws RestApiInvalidDataException {
        if (from.isAfter(to)) {
            throw new RestApiInvalidDataException(MessageConstants.DATE_RANGE_INVALID);
        }
    }
}