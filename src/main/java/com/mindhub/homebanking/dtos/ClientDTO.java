package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    Long id;
    private String firstName;
    private String lastName;
    private String eMail;
    private Set<AccountDTO> accounts;



    public ClientDTO() {
    }

    public ClientDTO(Client client) {

        this.id = client.getId();

        this.firstName = client.getFirstName();

        this.lastName = client.getLastName();

        this.eMail = client.geteMail();

        this.accounts=client.getAccount().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());

    }
    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
