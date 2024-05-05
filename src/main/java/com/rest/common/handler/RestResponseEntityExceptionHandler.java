package com.rest.common.handler;

import com.rest.common.dto.response.RestApiResponse;
import com.rest.common.enums.RestApiStatusCode;
import com.rest.common.exception.RestApiInvalidDataException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({RestApiInvalidDataException.class})
    public ResponseEntity<RestApiResponse> handleInvalidDataException(Exception ex) {
        return buildResponse(RestApiStatusCode.CODE_422, ex.getMessage());
    }

    private ResponseEntity<RestApiResponse> buildResponse(RestApiStatusCode status, String message) {
        var response = RestApiResponse.builder().status(status).message(message).build();
        return ResponseEntity.status(RestApiStatusCode.CODE_200.getCode()).body(response);
    }
}
