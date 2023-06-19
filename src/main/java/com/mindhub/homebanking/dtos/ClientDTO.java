package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String eMail;
    private Set<AccountDTO> accounts;
    private  Set<ClientLoanDTO> loans;


    public ClientDTO(Client client) {

        this.id = client.getId();

        this.firstName = client.getFirstName();

        this.lastName = client.getLastName();

        this.eMail = client.geteMail();

        this.accounts=client.getAccount().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());

        this.loans=client.getClientLoans().stream().map(clientLoan-> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());

    }
    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String geteMail() {
        return eMail;
    }


}
