package com.decathlon.ecolededev.client;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ClientService {

	ClientRepository clientRepository;

	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public ClientModel createClient(ClientModel client) {
		ClientModel clientModel = clientRepository.saveAndFlush(client);

		return clientModel;
	}

	public List<ClientModel> getClients() {
		List<ClientModel> clients = clientRepository.findAll();
		return clients;
	}

	public ClientModel getOneClient(Long id, String username) throws Exception {
		// il faut vérifier que user = username
		if (clientRepository.getOne(id).getUsername().equals(username)) {
			return clientRepository.getOne(id);
		}
		
		throw new RuntimeException("Pas autorisé!!!!");
	}

	public void deleteOneClient(Long id) {
		clientRepository.deleteById(id);
	}

}
