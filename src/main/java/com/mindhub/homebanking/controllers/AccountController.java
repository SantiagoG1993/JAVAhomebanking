package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
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

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;


    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccountsDTO();
    }
        @GetMapping("/accounts/{id}")
        public AccountDTO getAccount(@PathVariable Long id) {
        return new AccountDTO(accountService.findByid(id));

        }
    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        String email=authentication.getName();
        Set<Account> clientAccounts=clientService.findByEmail(email).getAccounts();
        if (clientAccounts.size()>=3){
            return new ResponseEntity<>("Already have three accounts", HttpStatus.FORBIDDEN);
        }
            String randomNumber;
            do{
                Random random=new Random();
                randomNumber="VIN-"+random.nextInt(90000000);
            }while(accountService.findByNumber(randomNumber)!=null);

            Account account=new Account(randomNumber,LocalDate.now(),0.0);
            Client client=clientService.findByEmail(email);
            client.addAccount(account);
            accountService.saveAccount(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
}
