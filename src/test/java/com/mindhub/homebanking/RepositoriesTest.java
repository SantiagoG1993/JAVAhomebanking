package com.mindhub.homebanking;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

    @DataJpaTest
    @AutoConfigureTestDatabase(replace = NONE)
    public class RepositoriesTest {

        @Autowired
        LoanRepository loanRepository;
        @Autowired
        ClientRepository clientRepository;
        @Autowired
        AccountRepository accountRepository;
        @Autowired
        CardRepository cardRepository;
        @Autowired
        TransactionRepository transactionRepository;

        @Test
        public void existLoans(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans,is(not(empty())));
        }
        @Test
        public void existPersonalLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Automotive"))));
        }
        @Test
        public void existsClient(){
            List<Client> clients= clientRepository.findAll();
            assertThat(clients,is(not(empty())));
        }
        @Test
        public void existsAccount(){
            List<Account> accounts=accountRepository.findAll();
            assertThat(accounts, is(not(empty())));
        }
        @Test
        public void existsCard(){
            List<Card> cards=cardRepository.findAll();
            assertThat(cards, is(not(empty())));
        }
        @Test
        public void existsTransaction(){
            List<Transaction> transaction=transactionRepository.findAll();
            assertThat(transaction, is(not(empty())));
        }

    }
