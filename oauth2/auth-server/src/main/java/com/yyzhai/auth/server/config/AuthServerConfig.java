package com.yyzhai.auth.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		// 允许表单认证
		oauthServer.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// JdbcClientDetailsService可以动态管理数据库中客户端数据
		clients.inMemory().withClient("client-id")// clientId：（必须的）用来标识客户的Id。
				.secret("client-secret")// secret：（需要值得信任的客户端）客户端安全码，如果有的话。
				.redirectUris("http://localhost:9000/client/auth/redirect")// 客户端应用负责获取授权码的endpoint
				.authorizedGrantTypes("authorization_code")// 授权码模式
				.scopes("select").and().// scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
				withClient("client-t").authorizedGrantTypes("client_credentials").scopes("select")
				.secret("client-secret-t");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.tokenStore(new InMemoryTokenStore())
				// .tokenStore(new RedisTokenStore(redisConnectionFactory))
				.authenticationManager(authenticationManager)
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
	}
}
