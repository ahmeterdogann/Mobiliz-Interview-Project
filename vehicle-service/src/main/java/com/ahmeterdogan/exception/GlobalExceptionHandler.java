package com.ahmeterdogan.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.ok("Beklenmeyen bir hata oluştu: " + ex.getMessage());
    }

    @ExceptionHandler(VehicleServiceException.class)
    @ResponseBody
    public ResponseEntity<String> handleApiException(VehicleServiceException ex){
        ApiErrorMessages apiErrorMessages = ex.getApiErrorMessages();
        return new ResponseEntity<>(ex.getMessage(), apiErrorMessages.getHttpStatus());
    }

}
