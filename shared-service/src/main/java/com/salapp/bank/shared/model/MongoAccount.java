package com.salapp.bank.shared.model;

import jakarta.persistence.Column;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MongoAccount implements Account {

    @MongoId
    private String accountId;

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


}
