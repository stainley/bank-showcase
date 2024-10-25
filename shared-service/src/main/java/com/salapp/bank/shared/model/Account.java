package com.salapp.bank.shared.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Abstract base class representing a bank account.
 * <p>
 * This class provides common attributes and abstract methods for deposit and withdrawal operations.
 * Concrete account types <i>(for example, Checking, Savings)</i> should extend this class.
 *
 * @author Stainley Lebron
 * @since 22-10-2024
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "accounts")
@Document(collection = "accounts")
public abstract class Account {
    /**
     * Unique identifier for the account.
     * <p>
     * Automatically generated using PostgreSQL's IDENTITY strategy.
     * Also serves as the MongoDB document ID.
     */
    @Id // JPA annotation for PostgreSQL
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MongoId // MongoDB annotation
    private Long accountId;

    @NotNull
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @NotNull
    @NotBlank(message = "Balance cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal balance;

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    private boolean isActive;

    /**
     * Constructor for creating an account with initial values.
     *
     * @param accountId Unique account ID
     * @param userId    User ID associated with the account
     * @param balance   Initial account balance
     */
    protected Account(Long accountId, String userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    /**
     * Abstract method for depositing funds into the account.
     * <p>
     * Concrete implementations should handle deposit logic.
     *
     * @param amount Amount to deposit
     */
    public abstract void deposit(BigDecimal amount);

    /**
     * Abstract method for withdrawing funds from the account.
     * <p>
     * Concrete implementations should handle withdrawal logic.
     *
     * @param amount Amount to withdraw
     */
    public abstract void withdraw(BigDecimal amount);

}
