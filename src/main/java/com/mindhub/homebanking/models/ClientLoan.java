package com.mindhub.homebanking.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
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


    public ClientLoan() {
    }

    public ClientLoan(double amount, Integer payments) {
        this.amount = amount;
        this.payments = payments;
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