package com.salapp.bank.common.model;


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
public abstract class MongoAccount implements Account {

    @MongoId
    private String accountId;

    private String userId;

    private BigDecimal balance;

    @CreatedDate
    private LocalDateTime createdAt;

    private boolean isActive;

}
