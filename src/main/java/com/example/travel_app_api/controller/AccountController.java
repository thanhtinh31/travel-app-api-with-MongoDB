package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Account;
import com.example.travel_app_api.request.ChangePasswordRequest;
import com.example.travel_app_api.response.AccountResponse;
import com.example.travel_app_api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public List<AccountResponse> getListAccount(){
        List<AccountResponse> accounts= accountService.listAcount();
        return accounts;
    }
    @GetMapping("/getAccount/{id}")
    public Account getAccountById(@PathVariable String id){

        return accountService.getAccountById(id);
    }

    @GetMapping("/active")
    public List<AccountResponse> getAccountActive(){
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
        return accountService.changePassword(changePasswordRequest.getId(),changePasswordRequest.getOldPass(),changePasswordRequest.getNewPass());
    }
    @PostMapping("/changestatus")
    public String changeStatus(@RequestBody Account account){
        return accountService.changeStatus(account);
    }
    @PostMapping("/changetypeaccount")
    public String changeTypeAccount(@RequestBody Account account){
        return accountService.changeTypeAccount(account);
    }
    @PostMapping("/forgotpass/{email}")
    public Map<String,Object> forgotpass(@PathVariable String email){
        return accountService.forgetPassword(email);
    }
    @PutMapping()
    public Map<String,Object> update(@RequestBody Account account){
        return accountService.editAccount(account);
    }

}
