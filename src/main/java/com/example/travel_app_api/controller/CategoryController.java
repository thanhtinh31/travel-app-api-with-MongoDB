package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Category;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.service.CategoryService;
import com.example.travel_app_api.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public Page<Category> getListCategory(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                          @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                          @RequestParam(name = "sort", required = false, defaultValue = "id") String sort){
        Page<Category> categories= categoryService.findCategory(page,size,sort);
        return categories;
    }
    @PostMapping
    public Category addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }
    @GetMapping("/search")
    private List<Category> search(@RequestParam(name="key",required = false,defaultValue = "") String key){
        return categoryService.searchCategory(key);
    }

}
