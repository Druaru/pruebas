package com.drr.springboot.backend.apirest.service.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.drr.springboot.backend.apirest.dto.RegisteredUser;
import com.drr.springboot.backend.apirest.dto.SaveUser;
import com.drr.springboot.backend.apirest.dto.auth.AuthenticationRequest;
import com.drr.springboot.backend.apirest.dto.auth.AuthenticationResponse;
import com.drr.springboot.backend.apirest.exception.ObjectNotFoundException;
import com.drr.springboot.backend.apirest.persistence.repository.security.JwtTokenRepository;
import com.drr.springboot.backend.apirest.persistence.repository.security.UserService;
import com.drr.springboot.backend.apirest.persistence.security.JwtToken;
import com.drr.springboot.backend.apirest.persistence.security.User;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenRepository jwtRepository;

	public RegisteredUser registerOneCustomer(SaveUser newUser) {

		User user = userService.registerOneCustomer(newUser);
		String jwt = jwtService.generateToken(user, generateExtraClaims(user));
		saveUserToken(user,jwt);
		
		
		RegisteredUser userDto = new RegisteredUser();

		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setUsername(user.getUsername());
		userDto.setRole(user.getRole().getName());

		userDto.setJwt(jwt);

		return userDto;
	}

	private Map<String, Object> generateExtraClaims(User user) {
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("name", user.getName());
		extraClaims.put("role", user.getRole().getName());
		extraClaims.put("authorities", user.getAuthorities());

		return extraClaims;
	}

	public AuthenticationResponse login(AuthenticationRequest authRequest) {

		Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
				authRequest.getPassword());

		authenticationManager.authenticate(authentication);

		UserDetails user = userService.findOneByUsername(authRequest.getUsername()).get();

		String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));
		saveUserToken((User)user, jwt);
		
		AuthenticationResponse autheRsp = new AuthenticationResponse();
		autheRsp.setJwt(jwt);

		return autheRsp;

	}

	private void saveUserToken(User user, String jwt) {

		JwtToken token = new JwtToken();
		token.setToken(jwt);
		token.setUser(user);
		token.setExpiration(jwtService.extractExpiration(jwt));
		token.setValid(true);
		
		jwtRepository.save(token);
		
	}

	public boolean validateToken(String jwt) {

		try {
			jwtService.extractUsername(jwt);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	public User findLoggedInUser() {

		Authentication auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication();

		String username = (String) auth.getPrincipal();

		return userService.findOneByUsername(username)
				.orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));

	}

	public void logout(HttpServletRequest request) {

		String jwt = jwtService.extractJwtFromRequest(request);
		if (jwt == null || !StringUtils.hasText(jwt))
			return;

		Optional<JwtToken> token = jwtRepository.findByToken(jwt);

		if (token.isPresent() && token.get().isValid()) {
			token.get().setValid(false);
			jwtRepository.save(token.get());
		}

	}

}
