package com.rest.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
public enum RestApiStatusCode {
    CODE_200(200, "Success"),
    CODE_422(422, "Invalid Data"),
    CODE_500(500, "Internal Server Error");

    private int code;
    @Getter
    private String message;

    @JsonValue
    public int getCode() {
        return code;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RestApiStatusCode getByCode(int code) {
        return Arrays.stream(values())
                .filter(status -> status.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching RestApiStatusCode for code: " + code));
    }
}