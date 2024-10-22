package com.salapp.bank.accountservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "debit_cards")
public class DebitCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 16, max = 16)
    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    private CheckingAccount linkedAccount;

    public DebitCard(String cardNumber, LocalDate expirationDate, CheckingAccount linkedAccount) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.linkedAccount = linkedAccount;
    }

    public void makeTransaction(BigDecimal amount) {
        linkedAccount.withdraw(amount);
    }
}
