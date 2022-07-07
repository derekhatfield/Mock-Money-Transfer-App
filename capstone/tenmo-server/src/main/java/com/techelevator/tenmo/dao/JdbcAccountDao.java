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
    public BigDecimal getBalance(long accountId) {
        String sql = "SELECT balance FROM account WHERE account_id = ?;";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);

        return balance;
    }

    @Override
    public Long getAccountIdByUserId(long userId){
        String sql = "SELECT account_id FROM account WHERE user_id = ?;";
        Long accountId = jdbcTemplate.queryForObject(sql, Long.class, userId);

        return accountId;
    }


    @Override
    public Account getAccountByUserId(long userId){
        Account newAccount = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        if (result.next()){
            newAccount = mapRowToAccount(result);
        }
        return newAccount;
    }

    @Override
    public BigDecimal updateBalance(Account account) {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?;";
        jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());
        return account.getBalance();
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

}
