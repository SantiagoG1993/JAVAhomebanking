package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private double balance;
    private LocalDate creationDate;

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name="client_id")
     private Client client;

    @OneToMany(mappedBy = "account", fetch=FetchType.EAGER)
    Set<Transaction> transaction=new HashSet<>();

    private AccountType accountType;
    private boolean deleted;



    public Account(){}

    public Account(String number, double balance, LocalDate creationDate, AccountType accountType, boolean deleted) {
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.accountType = accountType;
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        this.transaction.add(transaction);
    }
    public Set<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(Set<Transaction> transaction) {
        this.transaction = transaction;
    }
    public  long getIdAccount() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
