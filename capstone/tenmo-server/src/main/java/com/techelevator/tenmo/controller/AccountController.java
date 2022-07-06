package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController {

    private AccountDao dao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.dao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "account/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id) {
        return dao.getBalance(id);
    }

    @RequestMapping(path = "userslist", method = RequestMethod.GET)
    public List<User> listUsers() {
        List<User> users = userDao.findAll();
        return users;
    }

}
