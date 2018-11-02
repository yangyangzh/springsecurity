package com.yyzhai.auth.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user_1").password("123456").authorities("USER").and().withUser("user_2")
				.password("123456").authorities("USER").and().passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		AuthenticationManager manager = super.authenticationManagerBean();
		return manager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers()
				// /oauth/authorize link
				// org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint
				// 必须登录过的用户才可以进行 oauth2 的授权码申请
				.antMatchers("/", "/home", "/login", "/oauth/authorize").and().authorizeRequests().anyRequest()
				.permitAll().and().formLogin().and().httpBasic().disable().exceptionHandling()
				.accessDeniedPage("/login?authorization_error=true").and()
				// TODO: put CSRF protection back into this endpoint
				.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable();
	}

}
