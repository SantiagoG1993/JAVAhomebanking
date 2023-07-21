package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.ClientLoan;
public class ClientLoanDTO {

    private Long id;
    private double amount;
    private Integer payments;
    private String name;
    private Long LoanId;
    private Integer currentPayments;
    private double currentAmount;


    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.name = clientLoan.getLoan().getName();
        this.LoanId = clientLoan.getLoan().getId();
        this.currentPayments= clientLoan.getCurrentPayments();
        this.currentAmount=clientLoan.getCurrentAmount();
    }

    public Integer getCurrentPayments() {
        return currentPayments;
    }

    public double getCurrentAmount() {
        return currentAmount;
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
