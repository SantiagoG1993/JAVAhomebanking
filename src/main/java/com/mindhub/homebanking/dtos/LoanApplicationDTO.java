package com.mindhub.homebanking.dtos;

import java.util.List;

public class LoanApplicationDTO {
    private String destinationAccount;
    private double amount;
    private Integer payments;
    private String name;

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }
}
