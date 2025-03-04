package com.landry.transfertdedevise.compte.services;

import com.landry.transfertdedevise.compte.dto.AccountDto;
import com.landry.transfertdedevise.compte.entity.Account;
import com.landry.transfertdedevise.security.entity.User;
import com.landry.transfertdedevise.compte.exception.errorexceptions.UserNotFoundException;
import com.landry.transfertdedevise.compte.mapper.AccountMapper;
import com.landry.transfertdedevise.compte.repository.AccountRepository;
import com.landry.transfertdedevise.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private final AccountMapper accountMapper;

    @Transactional
    public AccountDto addAccount(AccountDto accountDto){
        User user = userRepository.findById(accountDto.getUserId())
                .orElseThrow(()-> new UserNotFoundException("User not found "));

        Account account = accountMapper.toEntity(accountDto);
        account.setUser(user);
        return accountMapper.toDto(accountRepository.save(account));
    }
}
