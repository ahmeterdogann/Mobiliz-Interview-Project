package com.ahmeterdogan.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ApiError {
    CREATE_ROOT_GROUP_NOT_ALLOWED(HttpStatus.FORBIDDEN, "You are not authorized for making root group creation"),
    GROUP_WITH_THIS_NAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "Group with this name already exists" ),
    PARENT_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "Parent group not found"),
    USER_NOT_AUTHORIZED_PARENT_GROUP(HttpStatus.FORBIDDEN, "User not authorized for parent group"),
    USER_NOT_COMPANY_ADMIN(HttpStatus.FORBIDDEN, "User not company admin"),
    CANNOT_DELETE_ROOT_GROUP(HttpStatus.FORBIDDEN, "Root group cannot be deleted"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_ALREADY_AUTHORIZED_TO_GROUP(HttpStatus.CONFLICT, "User already authorized to group"),
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "Group not found"),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"),
    USER_NOT_FOUND_IN_COMPANY(HttpStatus.NOT_FOUND, "User not found in company"),;


    HttpStatus httpStatus;
    private String message;

}
