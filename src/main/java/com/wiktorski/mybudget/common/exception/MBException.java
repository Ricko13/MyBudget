package com.wiktorski.mybudget.common.exception;

public class MBException extends RuntimeException {

    public MBException(ExceptionType exceptionType){
        super(exceptionType.toString());
    }

}
