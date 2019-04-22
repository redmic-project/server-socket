package es.redmic.socket.config;

/*-
 * #%L
 * socket
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class Oauth2SecurityConfiguration extends ResourceServerConfigurerAdapter {

	private static final String SPARKLR_RESOURCE_ID = "sparklr";

	@Value("${oauth.check_token.endpoint}")
	String checkTokenEndpoint;
	@Value("${oauth.client.id}")
	String clientId;
	@Value("${oauth.client.secret}")
	String secret;

	@Primary
	@Bean
	public RemoteTokenServices tokenService() {
		RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setCheckTokenEndpointUrl(checkTokenEndpoint);
		tokenService.setClientId(clientId);
		tokenService.setClientSecret(secret);
		return tokenService;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.tokenServices(tokenService()).resourceId(SPARKLR_RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.anonymous().and().authorizeRequests().antMatchers(HttpMethod.GET, "/user/actuator/**").permitAll();
	}
}
