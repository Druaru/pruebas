package com.cursos.api.springsecuritycourse.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cursos.api.springsecuritycourse.dto.SaveUser;
import com.cursos.api.springsecuritycourse.exception.InvalidPasswordException;
import com.cursos.api.springsecuritycourse.persistence.entity.User;
import com.cursos.api.springsecuritycourse.persistence.repository.UserRepository;
import com.cursos.api.springsecuritycourse.persistence.util.Role;
import com.cursos.api.springsecuritycourse.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User registerOneCustomer(SaveUser newUser) {

		validatePassword(newUser);

		User user = new User();
		user.setPassword(passwordEncoder.encode(newUser.getPassword()));
		user.setUsername(newUser.getUsername());
		user.setName(newUser.getName());
		user.setRole(Role.ROLE_CUSTOMER);

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
