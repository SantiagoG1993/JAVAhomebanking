package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;





    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }
        @RequestMapping("/accounts/{id}")
        public AccountDTO getAccount(@PathVariable Long id) {
            return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
        }
    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        String email=authentication.getName();
        Set<Account> clientAccounts=clientRepository.findByEmail(email).getAccounts();
        if (clientAccounts.size()>=3){
            return new ResponseEntity<>("ya tiene 3 cuentas", HttpStatus.FORBIDDEN);
        }
            String randomNumber;
            do{
                Random random=new Random();
                randomNumber="VIN-"+random.nextInt(90000000);
            }while(accountRepository.findByNumber(randomNumber)!=null);

            Account account=new Account(randomNumber,LocalDate.now(),0.0);
            Client client=clientRepository.findByEmail(email);
            client.addAccount(account);
            accountRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
}
