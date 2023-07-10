package com.mindhub.homebanking.services;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface AccountService {
List<AccountDTO> getAccountsDTO();
Account findByid(Long id);
AccountDTO getAccountDTO();
void saveAccount(Account account);
Account findByNumber(String number);
    Account findByClientAndNumber(Client client, String number);


}
