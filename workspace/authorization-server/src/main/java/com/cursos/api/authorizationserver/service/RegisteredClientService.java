package com.cursos.api.authorizationserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import com.cursos.api.authorizationserver.exception.ObjectNotFoundException;
import com.cursos.api.authorizationserver.mapper.ClientAppMapper;
import com.cursos.api.authorizationserver.persistence.entity.repository.security.ClientAppRepository;
import com.cursos.api.authorizationserver.persistence.entity.security.ClientApp;

@Service
public class RegisteredClientService implements RegisteredClientRepository{

	@Autowired
	private ClientAppRepository clientAppRepository;
	
	@Override
	public void save(RegisteredClient registeredClient) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RegisteredClient findById(String id) {
		ClientApp clientApp = clientAppRepository.findByClientId(id)
		.orElseThrow(()-> new ObjectNotFoundException("Client not found"));
		return ClientAppMapper.toRegisteredClient(clientApp);
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		return this.findById(clientId);
	}

}
