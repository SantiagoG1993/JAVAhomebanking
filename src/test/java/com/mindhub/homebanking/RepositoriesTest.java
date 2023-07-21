package com.mindhub.homebanking;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
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
        public void testCreateClient(){
            Client newClient=new Client("Juan","Rodriguez","juan@mindhub.com","123");
            clientRepository.save(newClient);
            Client clientCreated=clientRepository.findByEmail("juan@mindhub.com");
            assertNotNull(clientCreated);
            assertNotNull(clientCreated.getId());
            assertEquals("Juan", clientCreated.getFirstName());
            assertEquals("Rodriguez", clientCreated.getLastName());
            assertEquals("juan@mindhub.com", clientCreated.getEmail());
        }
        @Test
        public void existsAccount(){
            List<Account> accounts=accountRepository.findAll();
            assertThat(accounts, is(not(empty())));
        }
        @Test
        public void testCreateAccount() {
            Account account = new Account("VIN-009", 1000.0,LocalDate.now(),AccountType.SAVING,false);
            accountRepository.save(account);
            Account accountSaved=accountRepository.findByNumber("VIN-009");
            assertNotNull(accountSaved.getIdAccount());
            assertNotNull(accountSaved.getCreationDate());
            assertNotNull(accountSaved.getIdAccount());
            assertNotNull(accountSaved.getBalance());
            assertEquals("VIN-009", accountSaved.getNumber());
        }
        @Test
        public void existsCard(){
            List<Card> cards=cardRepository.findAll();
            assertThat(cards, is(not(empty())));
        }
        @Test
        public void cardNumberDigits(){
            List <Card> cards=cardRepository.findAll();
            for (Card card : cards) {
                String cardNumber = card.getNumber();
                assertEquals(19, cardNumber.length());
            }
        }
        @Test
        public void existsTransaction(){
            List<Transaction> transaction=transactionRepository.findAll();
            assertThat(transaction, is(not(empty())));
        }
        @Test
        public void testDeleteTransaction() {
            Transaction newTransaction = new Transaction(TransactionType.CREDIT, LocalDateTime.now(),"DESCRIPTION",10.000,false,10000.0);
            transactionRepository.save(newTransaction);
            transactionRepository.delete(newTransaction);
            List<Transaction> transactions = transactionRepository.findAll();
            assertFalse(transactions.contains(newTransaction));
        }

    }
