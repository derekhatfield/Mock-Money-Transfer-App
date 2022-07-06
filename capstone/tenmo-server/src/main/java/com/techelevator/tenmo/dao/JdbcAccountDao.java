package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int userId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);

        return balance;
    }

    @Override
    public BigDecimal addToBalance(int userId, BigDecimal amountToAdd) {
        BigDecimal newBalance = getBalance(userId).add(amountToAdd);
       String sql = "UPDATE account SET balance=? WHERE user_id = ? ;";
       jdbcTemplate.update(sql, newBalance, userId);


        return getBalance(userId);
    }

    @Override
    public BigDecimal subtractFromBalance(int accountId, BigDecimal amountToSubtract) {
        return null;
    }


    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

}
