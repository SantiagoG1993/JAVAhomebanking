package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {



    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }

    @Transactional
    @RequestMapping(value = "/loan",method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){

        double amount=loanApplicationDTO.getAmount();
    String destinationAccount=loanApplicationDTO.getDestinationAccount();
    Integer payments=loanApplicationDTO.getPayments();
    String name=loanApplicationDTO.getName();
    Account accountDestination=accountRepository.findByNumber(destinationAccount);

    Loan requestedLoan=loanRepository.findByName(name);

    Client client=clientRepository.findByEmail(authentication.getName());
    if(amount==0 || destinationAccount.isBlank() || name.isBlank() || payments==0){
        return new ResponseEntity("Missing data", HttpStatus.FORBIDDEN);
    }
    if  (loanRepository.findByName(name)==null){
        return new ResponseEntity("Loan doesnt exist", HttpStatus.FORBIDDEN);
    }
    if(requestedLoan.getMaxAmount()<amount){
        return new ResponseEntity("amount exceeded", HttpStatus.FORBIDDEN);
    }
    if(!requestedLoan.getPayments().contains(payments)){
        return new ResponseEntity("wrong payments", HttpStatus.FORBIDDEN);
    }
    if(accountRepository.findByNumber(destinationAccount)==null){
            return new ResponseEntity("destination account doesnt exists", HttpStatus.FORBIDDEN);
    }
    if(accountRepository.findByClientAndNumber(client,destinationAccount)==null){
            return new ResponseEntity<>("this account does not belong to this client", HttpStatus.FORBIDDEN);
    }
    else{
        ClientLoan clientloan1 = new ClientLoan(amount+amount*0.2, payments);
        client.addClientLoan(clientloan1);
        requestedLoan.addClientLoan(clientloan1);
        Transaction transactionLoan=new Transaction(TransactionType.CREDIT, LocalDateTime.now(),name+"Loan approved",amount);

        transactionRepository.save(transactionLoan);
        clientLoanRepository.save(clientloan1);

        accountDestination.addTransaction(transactionLoan);
        accountDestination.setBalance(accountDestination.getBalance()+amount);

        return new ResponseEntity<>("Loan request created",HttpStatus.CREATED);
    }
}}
