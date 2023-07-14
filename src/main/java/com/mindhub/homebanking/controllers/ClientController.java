package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
    return clientService.getClientsDTO();
        }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClient(id);
    }

    @PostMapping(path = "/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (password.isBlank()) {
            return new ResponseEntity<>("Missing password.", HttpStatus.FORBIDDEN);
        }
        if(firstName.isBlank()){
            return new ResponseEntity<>("Missing First Name.", HttpStatus.FORBIDDEN);
        }
        if(email.isBlank()){
            return new ResponseEntity<>("Missing e-mail.", HttpStatus.FORBIDDEN);
        }
        if(lastName.isBlank()){
            return new ResponseEntity<>("Missing last name.", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("E-mail already in use", HttpStatus.FORBIDDEN);
        }
       Client client=new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);

        String randomNumber;
        do{
            Random random=new Random();
            randomNumber="VIN-"+random.nextInt(90000000);
        }while(accountService.findByNumber(randomNumber)!=null);

        Account account=new Account(randomNumber, LocalDate.now(),0.0);
        client.addAccount(account);
        accountService.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("clients/current")
    public ClientDTO getClient(Authentication authentication){
        Client client=clientService.findByEmail(authentication.getName());
         ClientDTO clientDTO=new ClientDTO(client);
         return clientDTO;
    }

}

