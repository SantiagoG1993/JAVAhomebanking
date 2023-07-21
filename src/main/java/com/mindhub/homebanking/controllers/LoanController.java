package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    LoanService loanService;
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    ClientLoanService clientLoanService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getLoans();
    }

    @Transactional
    @PostMapping(value = "/loan")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){

    double amount=loanApplicationDTO.getAmount();
    String destinationAccount=loanApplicationDTO.getDestinationAccount();
    Integer payments=loanApplicationDTO.getPayments();
    String name=loanApplicationDTO.getName();
    Account accountDestination=accountService.findByNumber(destinationAccount);

    Loan requestedLoan=loanService.findByName(name);

    Client client=clientService.findByEmail(authentication.getName());
    if(amount==0 || amount==' '){
        return new ResponseEntity("Please enter loan amount", HttpStatus.FORBIDDEN);
    }
    if(destinationAccount.isBlank() ){
        return new ResponseEntity("Please enter a destination account.", HttpStatus.FORBIDDEN);
    }
    if(name.isBlank()){
        return new ResponseEntity<>("Please select a loan.",HttpStatus.FORBIDDEN);
    }
    if(payments==0){
        return new ResponseEntity<>("Please select payments.",HttpStatus.FORBIDDEN);
    }
    if  (loanService.findByName(name)==null){
        return new ResponseEntity("Loan doesn't exist.", HttpStatus.FORBIDDEN);
    }
    if(requestedLoan.getMaxAmount()<amount){
        return new ResponseEntity("Amount exceeded.", HttpStatus.FORBIDDEN);
    }
    if(!requestedLoan.getPayments().contains(payments)){
        return new ResponseEntity("Wrong payments.", HttpStatus.FORBIDDEN);
    }
    if(accountService.findByNumber(destinationAccount)==null){
            return new ResponseEntity("Destination account doesn't exists.", HttpStatus.FORBIDDEN);
    }
    if(accountService.findByClientAndNumber(client,destinationAccount)==null){
            return new ResponseEntity<>("This account does not belong to this client.", HttpStatus.FORBIDDEN);
    }
    else{
        double currentAmountCredit=accountDestination.getBalance()+loanApplicationDTO.getAmount();
        double interestRate= requestedLoan.getPercentage()/100;

        ClientLoan clientloan1 = new ClientLoan(amount, payments,payments,amount+amount*interestRate);
        client.addClientLoan(clientloan1);
        requestedLoan.addClientLoan(clientloan1);
        Transaction transactionLoan=new Transaction(TransactionType.CREDIT, LocalDateTime.now(),name+" "+"Loan approved",amount,false,currentAmountCredit);

        transactionService.saveTransaction(transactionLoan);
        clientLoanService.saveClientLoan(clientloan1);

        accountDestination.addTransaction(transactionLoan);
        accountDestination.setBalance(accountDestination.getBalance()+amount);

        return new ResponseEntity<>("Loan request created",HttpStatus.CREATED);
    }
}

    @PostMapping(value="/loan/create")
    public ResponseEntity<Object> createLoanAdmin(  @RequestBody LoanDTO loanDTO){
        if(loanService.findByName(loanDTO.getName())!=null){
            return new ResponseEntity("Loan already exists",HttpStatus.FORBIDDEN);
        }
        if(loanDTO.getName().isBlank()){
            return new ResponseEntity<>("Please enter a name",HttpStatus.FORBIDDEN);
        }
        if(loanDTO.getPayments()==null){
            return new ResponseEntity<>("Please enter payments",HttpStatus.FORBIDDEN);
        }
        if(loanDTO.getMaxAmount()==0){
            return new ResponseEntity<>("Please enter the max amount",HttpStatus.FORBIDDEN);
        }
        if(loanDTO.getPercentage()==0){
            return new ResponseEntity<>("Please enter an interest rate",HttpStatus.FORBIDDEN);
        }
        else{
            List<Integer> payments = loanDTO.getPayments();
            Loan loanCreated=new Loan(loanDTO.getName(), loanDTO.getMaxAmount(),payments, loanDTO.getPercentage());
            loanService.saveLoan(loanCreated);
            return new ResponseEntity<>("Loan created",HttpStatus.OK);
        }

    }
}
