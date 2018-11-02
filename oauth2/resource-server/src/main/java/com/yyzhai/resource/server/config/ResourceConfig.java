package com.yyzhai.resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

	@Bean
	public RemoteTokenServices tokenServices() {
		final RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
		tokenService.setClientId("client-id");
		tokenService.setClientSecret("client-secret");
		return tokenService;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().antMatchers("/api/**").and().authorizeRequests().anyRequest().authenticated().and()
				.authorizeRequests().antMatchers("/api/resource").access("#oauth2.hasScope('select')");
	}

}
