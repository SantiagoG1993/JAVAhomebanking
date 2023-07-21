package com.mindhub.homebanking.dtos;

public class CardPaymentDTO {
    private String cardNumber;
    private int  cvv;
    private String cardholder;
    private String description;
    private double amount;

    public CardPaymentDTO() {
    }

    public CardPaymentDTO(String cardNumber, int cvv, String cardholder, String description, double amount) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.cardholder = cardholder;
        this.description = description;
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public String getCardholder() {
        return cardholder;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}
