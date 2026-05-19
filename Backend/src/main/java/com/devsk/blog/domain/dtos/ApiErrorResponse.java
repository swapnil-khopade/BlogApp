package com.devsk.blog.domain.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse {

    private int status;
    private String message;
    private List<FieldError> error;

    public static class FieldError{
        private String field;
        private String message;
    }
}
