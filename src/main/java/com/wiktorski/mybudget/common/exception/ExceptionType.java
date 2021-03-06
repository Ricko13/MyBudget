package com.wiktorski.mybudget.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionType {

    ENTITY_NOT_FOUND("ENTITY_NOT_FOUND"),
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND"),
    BAD_REQUEST("BAD_REQUEST"),
    NO_LOGGED_USER("NO_LOGGED_USER");

    private String error;

    @Override
    public String toString(){
        return this.error;
    }
}
