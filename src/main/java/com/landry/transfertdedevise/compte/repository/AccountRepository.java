package com.landry.transfertdedevise.compte.repository;

import com.landry.transfertdedevise.compte.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
