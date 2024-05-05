package com.rest.user.mapper;

import com.rest.common.exception.RestApiInvalidDataException;
import com.rest.common.util.DateConverter;
import com.rest.user.dto.UserDTO;
import com.rest.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(target = "birthDate", source = "birthDate", qualifiedByName = "getStringBirthDate")
    public abstract UserDTO toDTO(User user);

    @Mapping(target = "birthDate", source = "birthDate", qualifiedByName = "getFormattedBirthDate")
    public abstract User toEntity(UserDTO dto);

    @Named("getStringBirthDate")
    String getStringBirthDate(LocalDate birthDate) {
        return DateConverter.formatDate(birthDate);
    }

    @Named("getFormattedBirthDate")
    LocalDate getBirthDate(String birthDate) throws RestApiInvalidDataException {
        return DateConverter.parseDate(birthDate);
    }
}
