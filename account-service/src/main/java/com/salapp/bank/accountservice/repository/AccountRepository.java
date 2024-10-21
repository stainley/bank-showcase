package com.salapp.bank.accountservice.repository;

import com.salapp.bank.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
