package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private LocalDate fromDate;
    private LocalDate truDate;
    private int cvv;
    private String number;
    private String cardholder;
    private CardType type;
    private CardColor color;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() {
    }

    public Card(LocalDate fromDate, LocalDate truDate, int cvv, String number, String cardholder, CardType type, CardColor color) {
        this.fromDate = fromDate;
        this.truDate = truDate;
        this.cvv = cvv;
        this.number = number;
        this.cardholder = cardholder;
        this.type = type;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getTruDate() {
        return truDate;
    }

    public int getCvv() {
        return cvv;
    }

    public String getNumber() {
        return number;
    }

    public String getCardholder() {
        return cardholder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public Client getClient() {
        return client;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setTruDate(LocalDate truDate) {
        this.truDate = truDate;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
