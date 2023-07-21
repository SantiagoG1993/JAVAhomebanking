package com.mindhub.homebanking.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private double amount;
    private Integer payments;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;
    private Integer currentPayments;
    private double currentAmount;


    public ClientLoan() {
    }

    public ClientLoan(double amount, Integer payments,Integer currentPayments,double currentAmount) {
        this.amount = amount;
        this.payments = payments;
        this.currentPayments=currentPayments;
        this.currentAmount=currentAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Integer getCurrentPayments() {
        return currentPayments;
    }

    public void setCurrentPayments(Integer currentPayments) {
        this.currentPayments = currentPayments;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }
    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public Long getId() {
        return id;
    }

    public Loan getLoan() {
        return loan;
    }
}