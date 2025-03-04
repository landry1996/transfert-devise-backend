package com.landry.transfertdedevise.compte.exception.errorexceptions;

import com.landry.transfertdedevise.compte.exception.BaseException;

public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException(String message){
        super(message, 404);
    }
}