package com.drr.springboot.backend.apirest.config.security.authorization;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import com.drr.springboot.backend.apirest.persistence.repository.security.OperacionRepository;
import com.drr.springboot.backend.apirest.persistence.repository.security.UserService;
import com.drr.springboot.backend.apirest.persistence.security.Operation;
import com.drr.springboot.backend.apirest.persistence.security.User;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

	@Autowired
	private OperacionRepository operacionRepository;

	@Autowired
	private UserService userService;

	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication,
			RequestAuthorizationContext requestContext) {

		HttpServletRequest request = requestContext.getRequest();

		String url = extractUrl(request);

		String httpMethod = request.getMethod();
		boolean isPublic = isPublic(url, httpMethod);

		if (isPublic) {
			return new AuthorizationDecision(true);
		}

		boolean isGranted = isGranted(url, httpMethod, authentication.get());

		return new AuthorizationDecision(isGranted);
	}

	private boolean isGranted(String url, String httpMethod, Authentication authentication) {
		
		if (authentication == null || !(authentication instanceof JwtAuthenticationToken)) {
			throw new AuthenticationCredentialsNotFoundException("User not logged in");
		}

		List<Operation> operations = obtainOperations(authentication);

		boolean isGranted = operations.parallelStream().anyMatch(getOperationPredicate(url, httpMethod));

		System.out.println("IS GRANTED: " + isGranted);
		return isGranted;
	}

	private Predicate<? super Operation> getOperationPredicate(String url, String httpMethod) {
		return operation -> {
			String basePath = operation.getModule().getBasePath();

			Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
			Matcher matcher = pattern.matcher(url);

			return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
		};
	}

	private List<Operation> obtainOperations(Authentication authentication) {
		
		JwtAuthenticationToken authToken = (JwtAuthenticationToken) authentication;

		Jwt jwt = authToken.getToken();
		String username = jwt.getSubject();

		User user = userService.findOneByUsername(username)
				.orElseThrow(() -> new com.drr.springboot.backend.apirest.exception.ObjectNotFoundException(
						"User not found. Username: " + username));

		List<Operation> operations = user.getRole().getPermissions().stream()
				.map(grantedPermission -> grantedPermission.getOperation()).collect(Collectors.toList());

		List<String> scopes = extractScopes(jwt);
		
		if(!scopes.contains("ALL")) {
			operations.stream()
			.filter(operation -> scopes.contains(operation.getName()))
			.collect(Collectors.toList());
		}
		return operations;
	}

	private List<String> extractScopes(Jwt jwt) {

		List<String> scopes = new ArrayList<>();

		try {
			scopes = (List<String>) jwt.getClaims().get("scope");
		} catch (Exception e) {
			System.out.println("Hubo un problema al extraer los scopes del cliente");
		}

		return scopes;
	}

	private boolean isPublic(String url, String httpMethod) {
		List<Operation> publicAccessEndpoints = operacionRepository.findByPubliccAcces();

		boolean isPublic = publicAccessEndpoints.parallelStream().anyMatch(getOperationPredicate(url, httpMethod));
		System.out.println("IS PUBLIC " + isPublic);

		return isPublic;
	}

	private String extractUrl(HttpServletRequest request) {

		String contextPath = request.getContextPath();
		String url = request.getRequestURI();
		url = url.replace(contextPath, "");
		System.out.println(url);

		return url;
	}

}
