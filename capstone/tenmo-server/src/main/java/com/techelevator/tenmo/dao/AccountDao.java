package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import com.techelevator.tenmo.model.Account;

public interface AccountDao {

    BigDecimal getBalance(long accountId);

    Account getAccountByUserId(long userId);

    BigDecimal updateBalance(Account account);

    Long getAccountIdByUserId(long userId);

}
