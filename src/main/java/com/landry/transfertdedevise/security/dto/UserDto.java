package com.landry.transfertdedevise.security.dto;

import com.landry.transfertdedevise.compte.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {
    private Integer userId;
    private String name;
    private String email;
    private LocalDate createdAt = LocalDate.now();
    private List<Account> accounts;

}