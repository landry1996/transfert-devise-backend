package com.landry.transfertdedevise.compte.exception.errorexceptions;

import com.landry.transfertdedevise.compte.exception.BaseException;

public class UserAlreadyExistException extends BaseException {

    public UserAlreadyExistException(String message){
        super(message, 409);
    }
}