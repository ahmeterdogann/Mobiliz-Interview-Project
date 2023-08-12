package com.ahmeterdogan.exception;

import lombok.Getter;

@Getter
public class AuthServiceException extends RuntimeException{
    private ApiErrorMessages apiErrorMessages;

    public AuthServiceException(ApiErrorMessages apiErrorMessages) {
        this.apiErrorMessages = apiErrorMessages;
    }

}
