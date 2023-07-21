package com.mindhub.homebanking.dtos;

public class PaymentDTO {
        private String originAccount;
        private Long clientLoanId;

    public PaymentDTO() {
    }

    public PaymentDTO(String originAccount, Long clientLoanId) {
        this.originAccount = originAccount;
        this.clientLoanId = clientLoanId;
    }

    public String getOriginAccount() {
        return originAccount;
    }

    public Long getClientLoanId() {
        return clientLoanId;
    }
}
