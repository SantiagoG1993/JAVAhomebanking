package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.TransactionRequestDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequestMapping("/api")
@RestController
public class TransactionController {
    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;
    @Autowired
    TransactionService transactionService;

    @Transactional
@PostMapping(path = "/transaction")
    public ResponseEntity<Object> createTransaction(Authentication authentication, @RequestBody TransactionRequestDTO transactionRequestDTO){
        String description = transactionRequestDTO.getDescription();
        String destinationAccount = transactionRequestDTO.getDestinationAccount();
        String originAccount = transactionRequestDTO.getOriginAccount();
        double amount = transactionRequestDTO.getAmount();

    Client client=clientService.findByEmail(authentication.getName());
    Account accountOrigin=accountService.findByNumber(originAccount);
    Account accountDestination=accountService.findByNumber(destinationAccount);


    if(description.isBlank()){
        return new ResponseEntity<>("Please enter a description.", HttpStatus.FORBIDDEN);
    }
    if(destinationAccount.isBlank() || originAccount.isBlank()){
            return new ResponseEntity<>("Please enter a destination account.", HttpStatus.FORBIDDEN);
    }
    if(originAccount.isBlank()){
            return new ResponseEntity<>("Please enter a source account.", HttpStatus.FORBIDDEN);
    }
    if (originAccount.equals(destinationAccount)){
        return new ResponseEntity<>("Please enter a different destination account.", HttpStatus.FORBIDDEN);
    }
    if(accountService.findByNumber(originAccount)==null){
        return new ResponseEntity<>("Source account does not exists.", HttpStatus.FORBIDDEN);
    }
    if(accountService.findByClientAndNumber(client,originAccount)==null){
        return new ResponseEntity<>("This account does not belong to this client.", HttpStatus.FORBIDDEN);
    }
    if(accountService.findByNumber(destinationAccount)==null){
        return new ResponseEntity<>("Destination account does not exists.", HttpStatus.FORBIDDEN);
    }
    if(accountService.findByNumber(originAccount).getBalance()<amount){
        return new ResponseEntity<>("Insufficient funds.", HttpStatus.FORBIDDEN);
    }
    if(amount==0){
        return new ResponseEntity<>("Please enter an amount to transfer.", HttpStatus.FORBIDDEN);
    }

    else{
        Transaction transactionDebit=new Transaction(TransactionType.DEBIT, LocalDateTime.now(),description+originAccount,-amount);
        Transaction transactionCredit=new Transaction(TransactionType.CREDIT, LocalDateTime.now(),description+destinationAccount,amount);

        accountOrigin.addTransaction(transactionDebit);
        accountDestination.addTransaction(transactionCredit);

        transactionService.saveTransaction(transactionCredit);
        transactionService.saveTransaction(transactionDebit);

        accountOrigin.setBalance(accountOrigin.getBalance()-amount);
        accountDestination.setBalance(accountDestination.getBalance()+amount);
        accountService.saveAccount(accountOrigin);
        accountService.saveAccount(accountDestination);
        return new ResponseEntity<>("Transaction created",HttpStatus.CREATED);
    }
}
}
