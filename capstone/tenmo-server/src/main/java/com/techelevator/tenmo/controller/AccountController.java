package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

//@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("account/")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

  /*  @RequestMapping(path = "balance" , method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
        long userId = getUserId(principal);
        return dao.getAccountByUserId(userId).getBalance();
    }*/


    @RequestMapping(path = "balance/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id) {
        return accountDao.getBalance(id);
    }

    public long getUserId(Principal principal){
        return userDao.findByUsername(principal.getName()).getId();
    }

    @RequestMapping(path = "userslist", method = RequestMethod.GET)
    public List<User> listUsers() {
        List<User> users = userDao.findAll();
        return users;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public long getAccountIdFromUserId(@PathVariable long id){
        return accountDao.getAccountIdByUserId(id);
    }

    @RequestMapping(path = "balance/{id}", method = RequestMethod.PUT)
    public BigDecimal updateAccount(@RequestBody Account account, @PathVariable int id) {
        return accountDao.updateBalance(account);
    }

    @RequestMapping(path = "test/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable long id) {
        return accountDao.getAccountByUserId(id);
    }


}
