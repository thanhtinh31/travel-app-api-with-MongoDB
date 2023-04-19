package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Category;
import com.example.travel_app_api.model.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {
    @Query("{ 'name' : { $regex: /?0/ ,$options:'i' } }")
    List<Category> searchCategory(String key);
    @Query("{ 'name' : ?0 }")
    Category findByName(String name);
    @Query("{ 'status' : true }")
    List<Category> getListCategoryActive();
    @Aggregation(pipeline = {"{'$match':{'status': true}}","{ $limit : 3 }"})
    List<Category> getListCategoryHome();

}
