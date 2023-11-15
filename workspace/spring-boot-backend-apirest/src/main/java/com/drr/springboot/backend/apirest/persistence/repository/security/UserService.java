package com.drr.springboot.backend.apirest.persistence.repository.security;

import java.util.Optional;


import com.drr.springboot.backend.apirest.dto.SaveUser;
import com.drr.springboot.backend.apirest.persistence.security.User;


public interface UserService{
	
	User registerOneCustomer(SaveUser newUser);


	Optional<User> findOneByUsername(String username);

}
