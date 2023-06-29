package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
        return (args) -> {
            Client client1 = new Client("Melba", "Morel", "Melba@mindhub.com",passwordEncoder.encode("melba123"));
            Client client2 = new Client("Santiago", "Gamarra", "santiago.gamarra@gmail.com",passwordEncoder.encode("santi123"));
            Client admin=new Client("admin","admin","admin@admin.com", passwordEncoder.encode("123"));
            Account account1=new Account("VIN-001", LocalDate.now(),5000.0);
            Account account2=new Account("VIN-002",LocalDate.now().plusDays(1),7500.0);
            Account account3=new Account("VIN-003",LocalDate.now().plusDays(1),14000.0);
            Account account4=new Account("VIN-004",LocalDate.now().plusDays(1),7500.0);

            client1.addAccount(account1);
            client1.addAccount(account2);
            client1.addAccount(account3);
            client2.addAccount(account4);


            Transaction transaction1=new Transaction(TransactionType.CREDIT, LocalDateTime.now(),"description 1",1000.0);
            Transaction transaction2=new Transaction(TransactionType.DEBIT,LocalDateTime.now(),"description 2",-2500.0);
            Transaction transaction3=new Transaction(TransactionType.CREDIT,LocalDateTime.now(),"description 3",3500.20);
            Transaction transaction4=new Transaction(TransactionType.CREDIT,LocalDateTime.now(),"description 4",1000.0);
            Transaction transaction5=new Transaction(TransactionType.DEBIT,LocalDateTime.now(),"description 5",-3433.0);
            Transaction transaction6=new Transaction(TransactionType.CREDIT,LocalDateTime.now(),"description 5",15000.0);

            Card card1=new Card(LocalDate.now(),LocalDate.now().plusYears(5),122,"3325-6745-7876-4445","Melba Morel",CardType.DEBIT,CardColor.GOLD);
            Card card2=new Card(LocalDate.now(),LocalDate.now().plusYears(5),211,"2225-6724-56336-2889","Melba Morel",CardType.CREDIT,CardColor.TITANIUM);
            Card card3=new Card(LocalDate.now(),LocalDate.now().plusYears(5),113,"2255-5666-3634-1112","Santiago Gamarra",CardType.CREDIT,CardColor.SILVER);


            account1.addTransaction(transaction1);
            account1.addTransaction(transaction2);
            account1.addTransaction(transaction3);
            account2.addTransaction(transaction4);
            account2.addTransaction(transaction5);
            account4.addTransaction(transaction6);

            client1.addCard(card1);
            client1.addCard(card2);
            client2.addCard(card3);

            clientRepository.save(client1);
            clientRepository.save(client2);
            clientRepository.save(admin);
            cardRepository.save(card1);
            cardRepository.save(card2);
            cardRepository.save(card3);

            accountRepository.save(account1);
            accountRepository.save(account2);
            accountRepository.save(account3);
            accountRepository.save(account4);

            List<Integer> mortgagePayments=List.of(12,24,36,48,60);
            List<Integer> personalPayments=List.of(6,12,24);
            List<Integer> AutomotivePayments=List.of(6,12,24,36);

            Loan mortgage=new Loan("Mortgage",500000,mortgagePayments);
            Loan personal=new Loan("Personal",100000,personalPayments);
            Loan automotive=new Loan("Automotive",300000,AutomotivePayments);

            loanRepository.save(mortgage);
            loanRepository.save(personal);
            loanRepository.save(automotive);

            ClientLoan clientLoan1=new ClientLoan(400000,60);
            ClientLoan clientLoan2=new ClientLoan(50000,12);
            ClientLoan clientLoan3=new ClientLoan(100000,24);
            ClientLoan clientLoan4=new ClientLoan(200000,36);

            client1.addClientLoan(clientLoan1);
            mortgage.addClientLoan(clientLoan1);

            client1.addClientLoan(clientLoan2);
            personal.addClientLoan(clientLoan2);

            client2.addClientLoan(clientLoan3);
            personal.addClientLoan(clientLoan3);

            client2.addClientLoan(clientLoan4);
            automotive.addClientLoan(clientLoan4);


            clientLoanRepository.save(clientLoan1);
            clientLoanRepository.save(clientLoan2);
            clientLoanRepository.save(clientLoan3);
            clientLoanRepository.save(clientLoan4);

            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);
            transactionRepository.save(transaction3);
            transactionRepository.save(transaction4);
            transactionRepository.save(transaction5);
            transactionRepository.save(transaction6);



        };
    }
}


