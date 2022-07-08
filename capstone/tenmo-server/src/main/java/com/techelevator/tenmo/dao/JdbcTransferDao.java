package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    JdbcTemplate jdbcTemplate;
    UserDao userDao;


    private static final String SQL_SELECT_TRANSFER =  "SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.amount, " +
            "aFrom.account_id as fromAcct, aFrom.user_id as fromUser, aFrom.balance as fromBal, " +
            "aTo.account_id as toAcct, aTo.user_id as toUser, aTo.balance as toBal " +
            "FROM transfer t " +
            "INNER JOIN transfer_type tt ON t.transfer_type_id = tt.transfer_type_id " +
            "INNER JOIN transfer_status ts ON t.transfer_status_id = ts.transfer_status_id " +
            "INNER JOIN account aFrom on account_from = aFrom.account_id " +
            "INNER JOIN account aTo on account_to = aTo.account_id ";

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, UserDao userDao){
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public List<Transfer> findAll(long accountId){

        List<Transfer> transfers = new ArrayList<>();
        String sql = "WHERE aFrom.account_id = ? OR aTo.account_id = ? ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(SQL_SELECT_TRANSFER + sql, accountId, accountId);
        while(results.next()){
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }






    @Override
    public void createNewTransfer(Transfer newTransfer) {
        String sql = "INSERT INTO transfer(account_from, account_to, amount, transfer_type_id, transfer_status_id) " +
                "VALUES(? , ? , ? , 2, 2);";

        if(newTransfer.getAccountFrom() == newTransfer.getAccountTo()){
            System.out.println("You cannot send a transfer to yourself");
        }else {
            jdbcTemplate.update(sql, newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());
            System.out.println("Transfer completed");

        }

    }



    private Transfer mapRowToTransfer(SqlRowSet rowSet){

       Transfer transfer = new Transfer(rowSet.getLong("transfer_id"),rowSet.getString("transfer_status_desc"),rowSet.getString("transfer_type_desc"),
               userDao.getUserById(rowSet.getLong("fromUser")), userDao.getUserById(rowSet.getLong("toUser")),rowSet.getBigDecimal("amount"));


        return transfer;
    }

}
