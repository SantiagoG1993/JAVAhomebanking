package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.ClientLoan;
public class ClientLoanDTO {

    private Long id;
    private double amount;
    private Integer payments;
    private String name;
    private Long LoanId;


    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.name = clientLoan.getLoan().getName();
        this.LoanId = clientLoan.getLoan().getId();
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getLoanId() {
        return LoanId;
    }
}
