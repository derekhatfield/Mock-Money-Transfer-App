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

        System.out.println("object deserialized:");
        System.out.println(transfer);

        /*
        * TODO:
        *  find some way of transforming transfer.accountFrom to an account id.
        *  ditto for accountTo
        *
        * SUggestion; Make a method in one of your dao's whose sole job
        * is to translate between these two types of accounts.
        * */

        dao.createNewTransfer(transfer);

    }



}
