package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Account;
import com.example.travel_app_api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    public List<Account> listAcount(){
        return accountRepository.findAll();
    }
    public Map<String,Object> login(String email,String password){
        Map<String,Object> m=new HashMap<>();
        Account account=accountRepository.getAcountByEmail(email);
        if(account!=null){
            if(account.isStatus()){
                if(accountRepository.login(email,password)!=null){
                    m.put("account",accountRepository.login(email,password));
                    m.put("message","Đăng nhập thành công");
                    m.put("status","1");
                    return m;
                }
                else{
                    m.put("message","Mật khẩu không chính xác");
                }

            }else{
                m.put("message","Tài khoản đã bị khóa");

            }
        }
        else{
            m.put("message","Tài khoản không tồn tại");
        }
        m.put("status","0");
        return m;
    }

    public Map<String,Object> handleLoginFB(Account account){
        Map<String,Object> m=new HashMap<>();
        if(accountRepository.getAccountByIDFacebook(account.getIdFacebook())!=null)
        {
            m.put("message","Tài khoản đã có");
            Account account1 = accountRepository.getAccountByIDFacebook(account.getIdFacebook());
            account1.setImage(account.getImage());
            account1.setNameAccount(account.getNameAccount());
            accountRepository.save(account1);
            m.put("account",accountRepository.getAccountByIDFacebook(account.getIdFacebook()));
        }
        else{

            accountRepository.save(account);
            m.put("message","Tai khoan moi");
            m.put("account",account);
        }
        return m;
    }
    public List<Account> getActive(){
        return accountRepository.getAcountActive();
    }
    public Account addAccount(Account account){
        return accountRepository.save(account);
    }
    public Account editAccount(Account account){
        Account account1=accountRepository.findById(account.getId()).get();
        account1.setAddress(account.getAddress());
        account1.setPhoneNumber(account.getPhoneNumber());
        account1.setPassword(account.getPassword());
        return accountRepository.save(account1);
    }
    public String deleteAccount(String idAccount){
        accountRepository.deleteById(idAccount);
        return "Deleted";
    }
    public Account findAccountByEmail(String email){
        if(accountRepository.getAcountByEmail(email)==null){
           return null;
        }
        return accountRepository.getAcountByEmail(email);
    }
    public int verify(String email){
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        String emailBody="Ma xac thuc e mail la :"+code;
        String subjectEmail="Travel app Verify";
        emailSenderService.sendMail(email,subjectEmail,emailBody);
        return code;
    }
    public Map<String,Object> register(Account account){
        Map<String,Object> m=new HashMap<>();
        if (accountRepository.getAcountByEmail(account.getEmail())==null){
            int code=verify(account.getEmail());
            m.put("status","1");
            m.put("account",account);
            m.put("code",code);
        }
        else{
            m.put("message","Email đã được đăng ký");
            m.put("status","0");
        }
        return m;
    }
    public Map<String,Object> forgetPassword(String email){
        Map<String,Object> m=new HashMap<>();
        if(accountRepository.getAcountByEmail(email)!=null){
            int code=verify(email);
            m.put("status","1");
            m.put("code",code);
            m.put("account",accountRepository.getAcountByEmail(email));
        }else{
            m.put("message","Tài khoản không tồn tại");
            m.put("status","0");
        }
        return m;
    }
    public Map<String,Object> changePassword(String email,String oldPass,String newPass){
        Map<String,Object> m=new HashMap<>();
        Account account=accountRepository.getAcountByEmail(email);
        if(oldPass.equals(account.getPassword())){
            if(oldPass.equals(newPass)){
                m.put("message","Mật khẩu không được giống cũ");
            }
            else{
                //changePass
                account.setPassword(newPass);
                accountRepository.save(account);
                emailSenderService.sendMail(email,"Travel app thông báo","Mật khẩu tài khoản của bạn đã thay đổi vào lúc "+java.time.LocalDateTime.now());
                m.put("message","Thay đổi mật khẩu thành công, vui lòng đăng nhập lại");
                m.put("status","1");
                return m;
            }

        }
        else{
            m.put("message","Mật khẩu không trùng khớp");
        }
        m.put("status","0");
        return m;
    }





}
