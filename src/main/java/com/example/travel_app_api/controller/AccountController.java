package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Account;
import com.example.travel_app_api.request.ChangePasswordRequest;
import com.example.travel_app_api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;
    @GetMapping
    public List<Account> getListAccount(){
        List<Account> accounts= accountService.listAcount();
        return accounts;
    }
    @GetMapping("/active")
    public List<Account> getAccountActive(){
        return accountService.getActive();
    }
    @PostMapping
    public Account addAccount(@RequestBody Account account){
        return accountService.addAccount(account);
    }

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody Account account){
        return accountService.login(account.getEmail(),account.getPassword());
    }
    @GetMapping("/{email}")
    public int getAccountByEmail(@PathVariable String email){
        return accountService.verify(email,"");
    }
    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody Account account){
        return accountService.register(account);
    }
    @PostMapping("/loginFB")
    public Map<String,Object> loginFB(@RequestBody Account account){
        return accountService.handleLoginFB(account);
    }

    @PostMapping("/changepassword")
    public Map<String,Object> changPassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return accountService.changePassword(changePasswordRequest.getEmail(),changePasswordRequest.getOldPass(),changePasswordRequest.getNewPass());
    }

}
