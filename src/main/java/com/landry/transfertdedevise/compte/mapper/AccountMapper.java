package com.landry.transfertdedevise.compte.mapper;

import com.landry.transfertdedevise.compte.dto.AccountDto;
import com.landry.transfertdedevise.compte.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);
    Account toEntity(AccountDto accountDto);
}