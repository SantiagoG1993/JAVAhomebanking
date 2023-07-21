package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.PaymentDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @PostMapping(path = "/payment")
    ResponseEntity<Object> createPayment(Authentication authentication, @RequestBody PaymentDTO paymentDTO){
        Client client=clientService.findByEmail(authentication.getName());
        Account accountOrigin=accountService.findByNumber(paymentDTO.getOriginAccount());
        ClientLoan clientLoan=clientLoanRepository.getById(paymentDTO.getClientLoanId());
        double paymentAmount=clientLoan.getAmount()/clientLoan.getPayments();
        if(accountOrigin.getBalance()<paymentAmount){
            return new ResponseEntity<>("Insufficient funds",HttpStatus.FORBIDDEN);
        }
        if(clientLoan.getCurrentPayments()==0){
            return new ResponseEntity<>("No payments remaining",HttpStatus.FORBIDDEN);
        }
        if(clientLoan.getCurrentAmount()==0){
            return new ResponseEntity<>("You already finished the payment",HttpStatus.FORBIDDEN);
        }


        accountOrigin.setBalance(accountOrigin.getBalance()-paymentAmount);
        double currentAmountTransaction=accountOrigin.getBalance()-paymentAmount;
        Transaction transaction= new Transaction(TransactionType.DEBIT,LocalDateTime.now(),"Loan payment",paymentAmount,false,currentAmountTransaction);
        accountOrigin.addTransaction(transaction);
        clientLoan.setCurrentPayments(clientLoan.getCurrentPayments()-1);
        clientLoan.setCurrentAmount((int) (clientLoan.getCurrentAmount()-paymentAmount));
        client.addClientLoan(clientLoan);
        transactionService.saveTransaction(transaction);
        accountService.saveAccount(accountOrigin);
        clientLoanRepository.save(clientLoan);

        return new ResponseEntity<>("Payment Correct", HttpStatus.OK);
    }
}
