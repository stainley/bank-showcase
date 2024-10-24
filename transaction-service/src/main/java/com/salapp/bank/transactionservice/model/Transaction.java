package com.salapp.bank.transactionservice.model;


import com.salapp.bank.common.model.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Document(collation = "transactions")
public class Transaction {

    @Id
    private Long id;

    @Field("amount")
    private BigDecimal amount;

    @Field("transaction_type")
    TransactionType transactionType;

    @DBRef
    @Field("source_account")
    private Account sourceAccount;

    @DBRef
    @Field("destination_account")
    private Account destinationAccount;

    @Field(value = "transaction_date")
    private LocalDateTime transactionDate;

    public Transaction(BigDecimal amount, TransactionType transactionType, Account sourceAccount, Account destinationAccount, LocalDateTime transactionDate) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.transactionDate = transactionDate;
    }

}
