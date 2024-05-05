package com.rest.user.controller;

import com.rest.common.enums.RestApiStatusCode;
import com.rest.common.exception.RestApiInvalidDataException;
import com.rest.user.dto.request.UserRequest;
import com.rest.user.dto.response.UserResponse;
import com.rest.user.dto.response.UsersResponse;
import com.rest.user.mapper.UserMapper;
import com.rest.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    /**
     * API to add User
     *
     * @param userRequest ({@link UserRequest}) - which consist of userId
     *                    <br>
     *                    <b>Request Example:</b> 	{
     *                    <br> 		"user": {
     *                    "email" : "illia.popov@gmail.com",                 /required
     *                    "firstName" : "Illia",                             /required
     *                    "lastName" : "Popov",                              /required
     *                    "birthDate" : "23-06-2001",                        /required
     *                    "address" : "Tarasa Shevchenka Blvd, 26",
     *                    "phoneNumber" : "+380990089234"
     *                    <p>
     *                    }
     *                    <br>	}
     * @return UserResponse
     * @author Illia Popov
     * <br>
     * <b>Address:</b> /users
     */
    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest) throws RestApiInvalidDataException {
        var createdUser = userService.createUser(userMapper.toEntity(userRequest.getUser()));
        return UserResponse.builder()
                .user(userMapper.toDTO(createdUser))
                .status(RestApiStatusCode.CODE_200)
                .message("Request has been processed successfully")
                .build();
    }

    /**
     * API to add User
     *
     * @param userRequest ({@link UserRequest}) - which consist of userId
     *                    <br>
     *                    <b>Request Example:</b> 	{
     *                    <br> 		"user": {
     *                    "email" : "illia.popov@gmail.com",                 /required
     *                    "firstName" : "Illia",                             /required
     *                    "lastName" : "Popov",                              /required
     *                    "birthDate" : "23-06-2001",                        /required
     *                    "address" : "Tarasa Shevchenka Blvd, 26",
     *                    "phoneNumber" : "+380990089234"
     *                    <p>
     *                    }
     *                    <br>	}
     * @return UserResponse
     * @author Illia Popov
     * <br>
     * <b>Address:</b> /users/1
     */
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable(value = "id") Long id, @RequestBody UserRequest userRequest) throws RestApiInvalidDataException {
        var updatedUser = userService.updateUser(id, userMapper.toEntity(userRequest.getUser()));
        return UserResponse.builder()
                .user(userMapper.toDTO(updatedUser))
                .status(RestApiStatusCode.CODE_200)
                .message("Request has been processed successfully")
                .build();
    }

    /**
     * API to add User
     *
     * @param userRequest ({@link UserRequest}) - which consist of userId
     *                    <br>
     *                    <b>Request Example:</b> 	{
     *                    <br> 		"user": {
     *                    "email" : "illia.popov@gmail.com",                 /required
     *                    "firstName" : "Illia",                             /required
     *                    "lastName" : "Popov",                              /required
     *                    "birthDate" : "23-06-2001",                        /required
     *                    "address" : "Tarasa Shevchenka Blvd, 26",
     *                    "phoneNumber" : "+380990089234"
     *                    <p>
     *                    }
     *                    <br>	}
     * @return UserResponse
     * @author Illia Popov
     * <br>
     * <b>Address:</b> /users/1
     */
    @PatchMapping("/{id}")
    public UserResponse updateUserFields(@PathVariable(value = "id") Long id, @RequestBody UserRequest userRequest) throws RestApiInvalidDataException {
        var updatedUser = userService.updateUserFields(id, userMapper.toEntity(userRequest.getUser()));
        return UserResponse.builder()
                .user(userMapper.toDTO(updatedUser))
                .status(RestApiStatusCode.CODE_200)
                .message("Request has been processed successfully")
                .build();
    }

    /**
     * API to add User
     *
     * @param userRequest ({@link UserRequest}) - which consist of userId
     *                    <br>
     *                    <b>Request Example:</b> 	{
     *                    <br> 		"user": {
     *                    "email" : "illia.popov@gmail.com",                 /required
     *                    "firstName" : "Illia",                             /required
     *                    "lastName" : "Popov",                              /required
     *                    "birthDate" : "23-06-2001",                        /required
     *                    "address" : "Tarasa Shevchenka Blvd, 26",
     *                    "phoneNumber" : "+380990089234"
     *                    <p>
     *                    }
     *                    <br>	}
     * @return UserResponse
     * @author Illia Popov
     * <br>
     * <b>Address:</b> /users/1
     */
    @DeleteMapping("/{id}")
    public UserResponse deleteUser(@PathVariable(value = "id") Long id) throws RestApiInvalidDataException {
        userService.deleteUser(id);
        return UserResponse.builder()
                .status(RestApiStatusCode.CODE_200)
                .message("Request has been processed successfully")
                .build();
    }

    @GetMapping
    public UsersResponse getUsersByBirthDateRanges(@RequestParam(value = "fromDate") String fromDate, @RequestParam(value = "toDate") String toDate) throws RestApiInvalidDataException {
        var users = userService.getUsersByBirthDateRange(fromDate, toDate);
        return UsersResponse.builder()
                .users(users.stream().map(userMapper::toDTO).toList())
                .status(RestApiStatusCode.CODE_200)
                .message("Request has been processed successfully")
                .build();
    }
}