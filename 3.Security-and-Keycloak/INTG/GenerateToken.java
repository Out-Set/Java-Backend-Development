package com.spring.security.Controller;

import com.spring.security.Entity.AuthRequest;
import com.spring.security.Entity.Token;
import com.spring.security.Entity.UserInfo;
import com.spring.security.Repository.TokenRepository;
import com.spring.security.Repository.UserInfoRepository;
import com.spring.security.Service.JwtService;
import com.spring.security.Service.TokenService;
import com.spring.security.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/authenticate")
public class GenerateToken {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if(authentication.isAuthenticated()) {
            String token =  jwtService.generateToken(authRequest.getUsername());

            // Save token
            Token tkn = new Token();
//            int count = (int)tokenRepository.count();
//            tkn.setId(++count);
            tkn.setToken(token);
//            tkn.setRevoked(false);
//            tkn.setExpired(false);
            UserInfo userInfo = userInfoService.getUserByName(authRequest.getUsername());
            tkn.setUserInfo(userInfo);
            userInfo.getTokens().add(tkn);

            tokenService.saveToken(tkn);

            return token;

        } else {
            throw new UsernameNotFoundException("invalid user request!");
        }
    }

    @PostMapping("/logout")
    public String logoutUser(){
        return "User logout successfully!";
    }
}
