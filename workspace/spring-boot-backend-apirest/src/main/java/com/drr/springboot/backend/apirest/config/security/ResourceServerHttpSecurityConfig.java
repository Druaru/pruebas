package com.drr.springboot.backend.apirest.config.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class ResourceServerHttpSecurityConfig {
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private AuthorizationManager<RequestAuthorizationContext> authorizationManager;

	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuerUri;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		SecurityFilterChain filterChain = http 
				.cors(withDefaults())
				.csrf(csrfConfig -> csrfConfig.disable())
				.sessionManagement(
						sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authReqConfig -> {
					authReqConfig.anyRequest().access(authorizationManager);
				})
				.exceptionHandling(exceptionConfig -> {
					exceptionConfig.authenticationEntryPoint(authenticationEntryPoint);
					exceptionConfig.accessDeniedHandler(accessDeniedHandler);
				})
				.oauth2ResourceServer(oauth2ResourceServerConfig -> {
					oauth2ResourceServerConfig.jwt(jwtConfig -> jwtConfig.decoder(JwtDecoders.fromIssuerLocation(issuerUri)));
				})
				.build();
		return filterChain;

	}
	
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("permissions");
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
		
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		
		return jwtAuthenticationConverter;
		
	}


}
