package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    void createNewTransfer(Transfer transfer);

    List<Transfer> findAll(long accountId);

    Transfer getTransferByTransferId(long transferId);

}
