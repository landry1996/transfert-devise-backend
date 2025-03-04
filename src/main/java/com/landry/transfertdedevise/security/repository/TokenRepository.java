package com.landry.transfertdedevise.security.repository;

import com.landry.transfertdedevise.security.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
    SELECT t FROM Token t 
    WHERE t.user.id = :id 
    AND (t.expired = false AND t.revoked = false)
    """)
    List<Token> findAllValidTokenByUser(@Param("id") Integer id);

    Optional<Token> findByToken(String token);
}