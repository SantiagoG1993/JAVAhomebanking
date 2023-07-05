package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionRequestDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
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
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
@RequestMapping(path = "/transaction",method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(Authentication authentication, @RequestBody TransactionRequestDTO transactionRequestDTO){
        String description = transactionRequestDTO.getDescription();
        String destinationAccount = transactionRequestDTO.getDestinationAccount();
        String originAccount = transactionRequestDTO.getOriginAccount();
        double amount = transactionRequestDTO.getAmount();

    Client client=clientRepository.findByEmail(authentication.getName());
    Account accountOrigin=accountRepository.findByNumber(originAccount);
    Account accountDestination=accountRepository.findByNumber(destinationAccount);


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
    if(accountRepository.findByNumber(originAccount)==null){
        return new ResponseEntity<>("Source account does not exists.", HttpStatus.FORBIDDEN);
    }
    if(accountRepository.findByClientAndNumber(client,originAccount)==null){
        return new ResponseEntity<>("This account does not belong to this client.", HttpStatus.FORBIDDEN);
    }
    if(accountRepository.findByNumber(destinationAccount)==null){
        return new ResponseEntity<>("Destination account does not exists.", HttpStatus.FORBIDDEN);
    }
    if(accountRepository.findByNumber(originAccount).getBalance()<amount){
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

        transactionRepository.save(transactionCredit);
        transactionRepository.save(transactionDebit);

        accountOrigin.setBalance(accountOrigin.getBalance()-amount);
        accountDestination.setBalance(accountDestination.getBalance()+amount);
        accountRepository.save(accountOrigin);
        accountRepository.save(accountDestination);
        return new ResponseEntity<>("Transaction created",HttpStatus.CREATED);
    }
}
}
