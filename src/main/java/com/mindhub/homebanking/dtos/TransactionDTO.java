package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;


import javax.persistence.*;
import java.time.LocalDateTime;

public class TransactionDTO{
        private Long id;
        private TransactionType type;
        private LocalDateTime creationDate;
        private String description;
        private Account account;
        private double amount;
        private boolean deleted;
        private double currentAmount;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction) {
        this.type = transaction.getType();
        this.creationDate = transaction.getCreationDate();
        this.description = transaction.getDescription();
        this.amount=transaction.getAmount();
        this.deleted=transaction.isDeleted();
        this.currentAmount= transaction.getCurrentAmount();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public double getAmount() {
        return amount;
    }
    public LocalDateTime getCreationDate() {
            return creationDate;
        }
        public String getDescription() {
            return description;
        }
        public TransactionType getType() {
            return type;
        }
        public void setAccount(Account account) {
            this.account = account;
        }
    }


