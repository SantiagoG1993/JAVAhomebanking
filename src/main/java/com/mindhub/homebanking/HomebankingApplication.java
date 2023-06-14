package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,TransactionRepository transactionRepository) {
        return (args) -> {
            Client client1 = new Client("Melba", "Morel", "Melba@mindhub.com");
            Client client2 = new Client("Santiago", "Gamarra", "santiago.gamarra@gmail.com");
            Account account1=new Account("VIN001", LocalDate.now(),5000.0,client1);
            Account account2=new Account("VIN002",LocalDate.now().plusDays(1),7500.0,client1);
            Account account3=new Account("VIN003",LocalDate.now().plusDays(1),14000.0,client1);
            Account account4=new Account("VIN004",LocalDate.now().plusDays(1),7500.0,client1);

            Transaction transaction1=new Transaction(TransactionType.CREDIT, LocalDateTime.now(),"descripcion 1",1000.0,account1);
            Transaction transaction2=new Transaction(TransactionType.DEBIT,LocalDateTime.now(),"descripcion 2",-2500.0,account1);
            Transaction transaction3=new Transaction(TransactionType.CREDIT,LocalDateTime.now(),"descripcion 3",3500.20,account1);
            Transaction transaction4=new Transaction(TransactionType.CREDIT,LocalDateTime.now(),"descripcion 4",1000.0,account2);
            Transaction transaction5=new Transaction(TransactionType.DEBIT,LocalDateTime.now(),"descripcion 5",-3433.0,account2);

            clientRepository.save(client1);
            clientRepository.save(client2);
            accountRepository.save(account1);
            accountRepository.save(account2);
            accountRepository.save(account3);
            accountRepository.save(account4);
            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);
            transactionRepository.save(transaction3);
            transactionRepository.save(transaction4);
            transactionRepository.save(transaction5);

        };
    }
}


