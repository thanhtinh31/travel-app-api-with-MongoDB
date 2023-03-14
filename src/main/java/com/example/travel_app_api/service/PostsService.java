package com.example.travel_app_api.service;

import com.example.travel_app_api.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;
}
