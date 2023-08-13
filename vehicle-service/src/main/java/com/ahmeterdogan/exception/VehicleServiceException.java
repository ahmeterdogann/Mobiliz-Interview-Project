package com.ahmeterdogan.exception;

import lombok.Getter;

@Getter
public class VehicleServiceException extends RuntimeException{
    private ApiError apiError;

    public VehicleServiceException(ApiError apiError) {
        this.apiError = apiError;
    }

}
