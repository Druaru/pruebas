package com.drr.springboot.backend.apirest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drr.springboot.backend.apirest.dto.auth.AuthenticationRequest;
import com.drr.springboot.backend.apirest.dto.auth.AuthenticationResponse;
import com.drr.springboot.backend.apirest.dto.auth.LogoutResponse;
import com.drr.springboot.backend.apirest.persistence.security.User;
import com.drr.springboot.backend.apirest.service.auth.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PreAuthorize("permitAll")
	@GetMapping("/validate-token")
	public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
		boolean isTokenValid = authenticationService.validateToken(jwt);
		return ResponseEntity.ok(isTokenValid);
	}

	//@CrossOrigin
	@PreAuthorize("permitAll")
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody @Valid AuthenticationRequest authenticationRequest) {

		AuthenticationResponse rsp = authenticationService.login(authenticationRequest);

		return ResponseEntity.ok(rsp);

	}

	@PostMapping("/logout")
	public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {

		authenticationService.logout(request);
		return ResponseEntity.ok(new LogoutResponse("Logout exitoso"));
	}

//    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR','CUSTOMER')")
	@PreAuthorize("hasAuthority('READ_MY_PROFILE')")
	@GetMapping("/profile")
	public ResponseEntity<User> findMyProfile() {
		User user = authenticationService.findLoggedInUser();
		return ResponseEntity.ok(user);
	}

}
