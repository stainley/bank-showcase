package com.salapp.bank.shared.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class JpaAccount implements Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long accountId;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @NotNull
    @NotBlank(message = "Balance cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal balance;

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private boolean isActive;

    public JpaAccount(Long accountId, String userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }
}
