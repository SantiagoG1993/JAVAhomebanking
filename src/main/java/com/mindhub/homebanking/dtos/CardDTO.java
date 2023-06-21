package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private LocalDate fromDate;
    private LocalDate truDate;
    private int cvv;
    private String number;
    private String cardholder;
    private CardType type;
    private CardColor color;

    private Client client;

    public CardDTO() {
    }

    public CardDTO(Card card) {
        id = card.getId();
        fromDate = card.getFromDate();
        truDate = card.getTruDate();
        cvv = card.getCvv();
        number = card.getNumber();
        cardholder = card.getCardholder();
        type = card.getType();
        color = card.getColor();
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


}
