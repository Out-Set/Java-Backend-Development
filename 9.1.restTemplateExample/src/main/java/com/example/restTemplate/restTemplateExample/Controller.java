package com.example.restTemplate.restTemplateExample;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {
    

    @RequestMapping("/gitHub/users/{login}")
    public GitHubUser get(@PathVariable("login") String login) {

        RequestEntity<GitHubUser> response = restTemplate.getForEntity(String.format("https://api.github.com/users/%s", login), GitHubUser.class);
        System.out.println(response.getHeaders());
        System.out.println(response.getStatusCode());
        
        return response.getBody();
    }
}
