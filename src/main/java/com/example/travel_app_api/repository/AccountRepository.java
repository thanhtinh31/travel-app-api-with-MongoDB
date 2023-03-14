package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<Account,String> {
    @Query("{'email' : ?0 }")
    public Account getAcountByEmail(String email);

    @Query("{'idFacebook' : ?0 }")
    public Account getAccountByIDFacebook(String idFB);
    @Query("{'status' : true }")
    public List<Account> getAcountActive();

    @Query("{'email' : ?0 ,'password' : ?1 }")
    public Account login(String email,String password);


}
