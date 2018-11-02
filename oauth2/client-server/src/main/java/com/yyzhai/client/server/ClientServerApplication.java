package com.yyzhai.client.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientServerApplication {

	public static void main(String[] args) {
        SpringApplication.run(ClientServerApplication.class, args);
	}


    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
