package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping(path="/accounts/current")
    public List<AccountDTO> getAccountsDTO(Authentication authentication){
      Client client= clientService.findByEmail(authentication.getName());
      List<Account> accounts=client.getAccounts().stream().filter(account ->!account.isDeleted()).collect(Collectors.toList());
      List<AccountDTO> accountsNonDeleted=accounts.stream().map(account -> new AccountDTO(account)) .collect(Collectors.toList());
      return accountsNonDeleted;
    };
    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam AccountType accountType) {
        String email=authentication.getName();
        Set<Account> clientAccounts=clientService.findByEmail(email).getAccounts();
        if (clientAccounts.size()>=3 && clientAccounts.stream().noneMatch(account -> account.isDeleted())){
            return new ResponseEntity<>("Already have three accounts", HttpStatus.FORBIDDEN);
        }
            String randomNumber;
            do{
                Random random=new Random();
                randomNumber="VIN-"+random.nextInt(90000000);
            }while(accountService.findByNumber(randomNumber)!=null);

         Account account=new Account(randomNumber,0.0,LocalDate.now(),accountType,false);
            Client client=clientService.findByEmail(email);
            client.addAccount(account);
            accountService.saveAccount(account);
            return new ResponseEntity<>("Account created",HttpStatus.CREATED);
        }
    @PutMapping(path = "/accounts/delete")
    public ResponseEntity<Object> deleteAccounts(Authentication authentication, @RequestBody AccountDTO accountDTO) {
        String email = authentication.getName();
        Client client = clientService.findByEmail(email);
        Account account = accountService.findByNumber(accountDTO.getNumber());
        Set<Account> nonDeletedAccount=client.getAccounts().stream().filter(accounts -> !accounts.isDeleted()).collect(Collectors.toSet());

        if(nonDeletedAccount.size() == 1 ){
            return new ResponseEntity<>("You have to have at least one account",HttpStatus.FORBIDDEN);
        }
        Set<Transaction> transactions = account.getTransaction();

        for (Transaction transaction : transactions) {
            transaction.setDeleted(true);
        }
        account.setDeleted(true);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Account deleted",HttpStatus.OK);

    }


}
