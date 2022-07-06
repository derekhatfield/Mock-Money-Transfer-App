package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    private TransferDao dao;

    public TransferController(TransferDao transferDao){
        this.dao = transferDao;
    }

    @RequestMapping(path = "transfer/", method = RequestMethod.POST)
    public void createTransfer(@RequestBody Transfer transfer){
        dao.sendMoney(transfer);

    }



}