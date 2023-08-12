package com.ahmeterdogan.exception;

import lombok.Getter;

@Getter
public class GroupServiceException extends RuntimeException{
    private ApiErrorMessages apiErrorMessages;

    public GroupServiceException(ApiErrorMessages apiErrorMessages) {
        this.apiErrorMessages = apiErrorMessages;
    }

}
