package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Loan;
import java.util.List;

public class LoanDTO {

    private Long id;
    private String name;
    private double maxAmount;
    private List<Integer> payments;
    private double percentage;


    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        id = loan.getId();
        name = loan.getName();
        maxAmount = loan.getMaxAmount();
        payments = loan.getPayments();
        percentage=loan.getPercentage();
    }

    public double getPercentage() {
        return percentage;
    }

    public Long getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
