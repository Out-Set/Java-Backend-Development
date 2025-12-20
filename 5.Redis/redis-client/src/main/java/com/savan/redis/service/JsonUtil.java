package com.savan.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsonUtil {

	@Autowired
	private ObjectMapper objectMapper;

	// Object to JSON
	public <T> String convertObjectToJson(T object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	// JSON-Array/JSON-String to Object
	public <T> T convertJsonToObject(String json, TypeReference<T> typeReference) {
		try {
			 return objectMapper.readValue(json, typeReference);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	// If only JSON-String to Object
	public <T> T convertJsonToObject(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}