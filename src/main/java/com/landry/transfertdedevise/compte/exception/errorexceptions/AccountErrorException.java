package com.landry.transfertdedevise.compte.exception.errorexceptions;

import com.landry.transfertdedevise.compte.exception.BaseException;

public class AccountErrorException extends BaseException {

    public AccountErrorException(String message){
        super(message, 500);
    }
}