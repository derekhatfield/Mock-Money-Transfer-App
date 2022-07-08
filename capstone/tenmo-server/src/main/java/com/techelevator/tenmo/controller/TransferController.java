package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@PreAuthorize("isAuthenticated()")
@RequestMapping("transfer/")
@RestController
public class TransferController {

    private TransferDao dao;

    public TransferController(TransferDao transferDao){
        this.dao = transferDao;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createTransfer(@RequestBody Transfer transfer){
        dao.createNewTransfer(transfer);

    }

    @RequestMapping(path = "{id}/transferslist", method = RequestMethod.GET)
    public List<Transfer> listTransfers(@PathVariable long id) {
        return dao.findAll(id);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@PathVariable long id) {
        return dao.getTransferByTransferId(id);
    }

}
