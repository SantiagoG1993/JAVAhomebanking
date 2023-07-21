package com.mindhub.homebanking.services;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);
    List<Transaction> findBetween(LocalDate date1, LocalDate date2);
}
