package com.ahmeterdogan.exception;

import lombok.Getter;

@Getter
public class VehicleServiceException extends RuntimeException{
    private ApiErrorMessages apiErrorMessages;

    public VehicleServiceException(ApiErrorMessages apiErrorMessages) {
        this.apiErrorMessages = apiErrorMessages;
    }

}
