package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import com.techelevator.tenmo.model.Account;

public interface AccountDao {

    BigDecimal getBalance(int accountId);

    BigDecimal addToBalance(int accountId, BigDecimal amountToAdd);

    BigDecimal subtractFromBalance(int accountId, BigDecimal amountToSubtract);



}
