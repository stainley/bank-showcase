package com.salapp.bank.accountservice.repository;

import com.salapp.bank.accountservice.model.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingAccount, Long> {

}
