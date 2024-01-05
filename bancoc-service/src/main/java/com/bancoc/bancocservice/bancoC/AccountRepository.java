package com.bancoc.bancocservice.bancoC;// Update the package declaration to match the expected package

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/* import model.Account; */

public interface AccountRepository extends JpaRepository<Account, String>{

    /* @Query("SELECT a FROM Account a WHERE a.clientId = ?1")
    Optional<Account> findByClientIdAndAccountId(String clientId, String accountId); */

    @Query("SELECT a FROM Account a WHERE a.accountNumber = ?1")
    Optional<Account> findByAccountId(String accountId);

    /* Object findById(String accountId);

    Account findByClientIdAndAccountId(String clientId, String accountId); */

}
