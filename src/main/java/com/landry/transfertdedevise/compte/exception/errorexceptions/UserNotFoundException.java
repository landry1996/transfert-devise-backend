package com.landry.transfertdedevise.compte.exception.errorexceptions;

import com.landry.transfertdedevise.compte.exception.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message){
        super(message, 404);
    }
}