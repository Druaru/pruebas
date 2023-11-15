package com.drr.springboot.backend.apirest.models.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.drr.springboot.backend.apirest.dto.SaveUser;
import com.drr.springboot.backend.apirest.exception.InvalidPasswordException;
import com.drr.springboot.backend.apirest.exception.ObjectNotFoundException;
import com.drr.springboot.backend.apirest.persistence.repository.security.UserRepository;
import com.drr.springboot.backend.apirest.persistence.repository.security.UserService;
import com.drr.springboot.backend.apirest.persistence.security.Role;
import com.drr.springboot.backend.apirest.persistence.security.User;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleService roleService;
	
	@Override
	public User registerOneCustomer(SaveUser newUser) {

		validatePassword(newUser);

		User user = new User();
		user.setPassword(passwordEncoder.encode(newUser.getPassword()));
		user.setUsername(newUser.getUsername());
		user.setName(newUser.getName());
		Role defaultRole = roleService.findDefaultRole()
				.orElseThrow(()->new ObjectNotFoundException("Role not found. Default Role"));
		
		user.setRole(defaultRole);

		return userRepository.save(user);
	}

	private void validatePassword(SaveUser newUser) {

		if (!StringUtils.hasText(newUser.getPassword()) || !StringUtils.hasText(newUser.getRepeatedPassword())) {
			throw new InvalidPasswordException("Password don't match");
		}

		if (!newUser.getPassword().equals(newUser.getRepeatedPassword())) {
			throw new InvalidPasswordException("Passwords don't match");
		}

	}

	@Override
	public Optional<User> findOneByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
