package com.ahmeterdogan.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.ahmeterdogan.exception.ApiError.UNEXPECTED_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GroupServiceException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorDTO> handleApiException(GroupServiceException ex){
        ApiError apiError = ex.getApiError();
        return new ResponseEntity<>(createApiErrorDto(apiError), apiError.getHttpStatus());
    }

    private ApiErrorDTO createApiErrorDto(ApiError apiError){
        return ApiErrorDTO.builder()
                .code(apiError.getHttpStatus().value())
                .message(apiError.getMessage())
                .build();
    }
}