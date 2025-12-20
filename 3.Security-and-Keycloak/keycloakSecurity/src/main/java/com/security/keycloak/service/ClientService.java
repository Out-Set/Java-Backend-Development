package com.security.keycloak.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.security.keycloak.entity.Client;
import com.security.keycloak.entity.ClientApis;
import com.security.keycloak.repository.ClientApisRepo;
import com.security.keycloak.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private ClientApisRepo clientApisRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createClient(Client client){
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepo.save(client);
        return "Client registered successfully!";
    }

    public String createManyClient(List<Client> client){
        for(Client clnt: client) {
            clnt.setPassword(passwordEncoder.encode(clnt.getPassword()));
        }
        clientRepo.saveAll(client);
        return "Clients are registered successfully!";
    }

    public List<Client> getAllClient() {
        return clientRepo.findAll();
    }
    
    public Object getApiDetails() {
		Client client = clientRepo.findById(Integer.parseInt("1")).get();
		List<ClientApis> clientApis = clientApisRepo.findByClient(client);
		List<Object> responseData = new LinkedList<Object>();
		Map<String, Object> dbData;
		for(ClientApis c : clientApis) {
			dbData = new LinkedHashMap<String, Object>();
			dbData.put("id",c.getId());
			dbData.put("apiName",c.getApi().getApiName());
			dbData.put("consumedHitsOfApi", c.getConsumedHitsOfApi());
			dbData.put("remainingHitsOfApi", c.getRemainingHitsOfApi());
			
			responseData.add(dbData);
		}
		return responseData;
	}

    public Client readClient(int id){
       return clientRepo.findById(id).get();
    }

    public String updateClient(Client client){
        Client existingClient = clientRepo.findById(client.getId()).get();
        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        existingClient.setPassword(passwordEncoder.encode(client.getPassword()));
        existingClient.setAddress(client.getAddress());
        existingClient.setTotalUsedHits(client.getTotalUsedHits());
        existingClient.setTotalRemainingHits(client.getTotalRemainingHits());
        existingClient.setUserList(client.getUserList());
        existingClient.setClientApisList(client.getClientApisList());
        clientRepo.save(existingClient);
        return "Client updated successfully!";
    }

    public String deleteClient(int id){
        clientRepo.deleteById(id);
        return "Client deleted successfully!";
    }


}
