package com.cursos.api.springsecuritycourse.service.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cursos.api.springsecuritycourse.dto.RegisteredUser;
import com.cursos.api.springsecuritycourse.dto.SaveUser;
import com.cursos.api.springsecuritycourse.dto.auth.AuthenticationRequest;
import com.cursos.api.springsecuritycourse.dto.auth.AuthenticationResponse;
import com.cursos.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.cursos.api.springsecuritycourse.persistence.entity.User;
import com.cursos.api.springsecuritycourse.service.UserService;

@Service
public class AuthenticationService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	public RegisteredUser registerOneCustomer(SaveUser newUser) {

		User user = userService.registerOneCustomer(newUser);

		RegisteredUser userDto = new RegisteredUser();

		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setUsername(user.getUsername());
		userDto.setRole(user.getRole().name());

		String jwt = jwtService.generateToken(user, generateExtraClaims(user));

		userDto.setJwt(jwt);

		return userDto;
	}

	private Map<String, Object> generateExtraClaims(User user) {
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("name", user.getName());
		extraClaims.put("role", user.getRole().name());
		extraClaims.put("authorities", user.getAuthorities());

		return extraClaims;
	}

	public AuthenticationResponse login(AuthenticationRequest authRequest) {

		Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
				authRequest.getPassword());

		authenticationManager.authenticate(authentication);

		UserDetails user = userService.findOneByUsername(authRequest.getUsername()).get();

		String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));

		AuthenticationResponse autheRsp = new AuthenticationResponse();
		autheRsp.setJwt(jwt);

		return autheRsp;

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

}
