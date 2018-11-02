package com.yyzhai.client.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AuthCallbackController {

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/client/auth/redirect")
	public String getToken(@RequestParam String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("code", code);
		params.add("client_id", "client-id");
		params.add("client_secret", "client-secret");
		params.add("redirect_uri", "http://localhost:9000/client/auth/redirect");
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", requestEntity,
				String.class);
		String token = response.getBody();
		return token;
	}
}
