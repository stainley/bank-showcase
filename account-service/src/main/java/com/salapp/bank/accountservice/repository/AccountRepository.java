package com.salapp.bank.accountservice.repository;

import com.salapp.bank.shared.model.JpaAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AccountRepository {//<T extends JpaAccount> extends JpaRepository<T, Long> {

}
