package com.salapp.bank.accountservice.service;


import java.util.List;

public interface IAccountService<T> {

    T createAccount(T account);

    T getAccountById(Long id);

    List<T> getAllAccounts();

    void deleteAccount(Long id);


}
