package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

//@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("account/")
public class AccountController {

    private AccountDao dao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.dao = accountDao;
        this.userDao = userDao;
    }

  /*  @RequestMapping(path = "balance" , method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
        long userId = getUserId(principal);
        return dao.getAccountByUserId(userId).getBalance();
    }*/


    @RequestMapping(path = "balance/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id) {
        return dao.getBalance(id);
    }

    public long getUserId(Principal principal){
        return userDao.findByUsername(principal.getName()).getId();
    }



    @RequestMapping(path = "userslist", method = RequestMethod.GET)
    public List<User> listUsers() {
        List<User> users = userDao.findAll();
        return users;
    }

}
