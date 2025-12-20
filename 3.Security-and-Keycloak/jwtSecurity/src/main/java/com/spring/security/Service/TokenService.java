package com.spring.security.Service;

import com.spring.security.Entity.Token;
import com.spring.security.Repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public String saveToken(Token token){
        tokenRepository.save(token);
        return "Token saved successfully!";
    }
}
