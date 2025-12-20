package com.spring.security.noauthservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoAuthApiService {
	
	@Autowired
    private NoAuthApiRepo noAuthApiRepo;
	
	@Getter
    private List<String> codes = new ArrayList<>();
    private List<String> endPoints = new ArrayList<>();

    // @PostConstruct
    public void loadAllCodeWithEndpoints() {
        List<NoAuthApi> apiList = noAuthApiRepo.findAll();
        if (!apiList.isEmpty()) {
            codes = apiList.stream()
                .map(NoAuthApi::getCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
            
            endPoints = apiList.stream()
            	    .map(NoAuthApi::getEndPoint)
            	    .filter(Objects::nonNull)
            	    .map(endpoint -> "/rest" + endpoint)
            	    .collect(Collectors.toList());
        }
        log.info("Loaded no-auth service codes: {}", codes);
        log.info("Loaded no-auth service endpoints: {}", endPoints);
    }

    public List<String> getEndPoints() {
        if(!endPoints.isEmpty()){
            return endPoints;
        }
    	loadAllCodeWithEndpoints();
        return endPoints;
    }
}
