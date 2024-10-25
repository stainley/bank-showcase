package com.salapp.bank.accountservice.repository;

import com.salapp.bank.accountservice.model.SavingAccount;
//import com.salapp.bank.common.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingAccount, Long> {

    /*@Query("SELECT account FROM Account account WHERE account.accountId = :accountId")
    Optional<Account> findAccountByAccountId(@Param("accountId") Long accountId);*/

}
