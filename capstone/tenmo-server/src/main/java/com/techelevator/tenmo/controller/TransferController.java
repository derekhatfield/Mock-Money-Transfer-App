package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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












}
