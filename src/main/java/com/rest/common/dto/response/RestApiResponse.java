package com.rest.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rest.common.enums.RestApiStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RestApiResponse {
    /**
     * id of the processed entity
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    private RestApiStatusCode status;
    private String message;
}
