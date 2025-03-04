package com.landry.transfertdedevise.compte.exception.errorexceptions;

import com.landry.transfertdedevise.compte.exception.BaseException;

public class AccountAlreadyExistException extends BaseException {

    public AccountAlreadyExistException(String message){
        super(message, 409);
    }
}