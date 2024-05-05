package com.rest.user.dto.response;

import com.rest.common.dto.response.RestApiResponse;
import com.rest.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UsersResponse extends RestApiResponse {
    private List<UserDTO> users;
}
