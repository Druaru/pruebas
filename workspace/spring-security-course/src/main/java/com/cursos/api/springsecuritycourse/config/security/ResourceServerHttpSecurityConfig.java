package com.cursos.api.springsecuritycourse.config.security;

//import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//import com.cursos.api.springsecuritycourse.persistence.util.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.RegexRequestMatcher;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//import com.cursos.api.springsecuritycourse.config.security.filter.JwtAuthenticationFilter;
//import com.cursos.api.springsecuritycourse.persistence.util.RolePermissionEnum;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class ResourceServerHttpSecurityConfig {
//
	@Autowired
    private AuthenticationProvider daoAuthProvider;
	
//	@Autowired
//	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
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
//				.authenticationProvider(daoAuthProvider)
//				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests(authReqConfig -> {
//					buildRequestMatcher(authReqConfig);
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

	
	
	

//	private void buildRequestMatcher(
//			AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
////		/*
////		 * Autorizacion de endpoints de products
////		 * */
////		
//		authReqConfig.requestMatchers(HttpMethod.GET,"/products")
//		.hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());
////		//.hasAuthority(RolePermission.READ_ALL_PRODUCTS.name());
////		
////
//		authReqConfig.requestMatchers(HttpMethod.GET,"/products/{productId}")
//		.hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());
////		//.hasAuthority(RolePermission.READ_ONE_PRODUCT.name());
////		
////
//		authReqConfig.requestMatchers(HttpMethod.POST,"/products")
//		.hasRole(RoleEnum.ADMINISTRATOR.name());
////		//.hasAuthority(RolePermission.CREATE_ONE_PRODUCT.name());
////		
//		authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}");
//		authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET,"/products/[0-9]*"))
//		.hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());
////		//.hasAuthority(RolePermission.UPDATE_ONE_PRODUCT.name());
////		
//		authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}/disabled")
//		.hasRole(RoleEnum.ADMINISTRATOR.name());
////		//.hasAuthority(RolePermission.DISABLE_ONE_PRODUCT.name());
////		
////		/*
////		 * Autorizacion de endpoints de categories
////		 * */
////		
//		authReqConfig.requestMatchers(HttpMethod.GET,"/categories")
//		.hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());
////		//.hasAuthority(RolePermission.READ_ALL_CATEGORIES.name());
////		
////
//		authReqConfig.requestMatchers(HttpMethod.GET,"/categories/{categoryId}")
//		.hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());
////		//.hasAuthority(RolePermission.READ_ONE_CATEGORY.name());
////		
////
//		authReqConfig.requestMatchers(HttpMethod.POST,"/categories")
//		.hasRole(RoleEnum.ADMINISTRATOR.name());
////		//.hasAuthority(RolePermission.CREATE_ONE_CATEGORY.name());
////		
//		authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}")
//		.hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());
////		//.hasAuthority(RolePermission.UPDATE_ONE_CATEGORY.name());
////		
//		authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}/disabled");
////		.hasRole(Role.ADMINISTRATOR.name());
////		//.hasAuthority(RolePermission.DISABLE_ONE_CATEGORY.name());
////		
//		authReqConfig.requestMatchers(HttpMethod.GET,"/auth/profile")
//		.hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name(),RoleEnum.CUSTOMER.name());
////		//.hasAuthority(RolePermission.READ_MY_PROFILE.name());
////		
////		
////		/*
////		 * Autorización endpoints publicos
////		 * */
//		
//		authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
//		authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
//		authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate-token").permitAll();
//
//		
//		authReqConfig.anyRequest().authenticated();
//	}

}
