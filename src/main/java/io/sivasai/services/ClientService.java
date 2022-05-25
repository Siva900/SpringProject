package io.sivasai.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import io.sivasai.dao.ClientDao;
import io.sivasai.model.ClientModel;

@Service
public class ClientService{
	
	@Autowired
	ClientDao ClientDao;
	
	public List<ClientModel> getAllClients() {
		List<ClientModel> clients = new ArrayList<>();
		clients = ClientDao.getAllClients();
		System.out.println("client service print: ");
		for (ClientModel client : clients) {
			System.out.println(client.toString());
		}
		return clients;
	}

	public ClientModel getClientById(String Id) {
		ClientModel client = new ClientModel();
		client = ClientDao.getClientById(Id);
		//System.out.println("client service print: ");
		//System.out.println(client);
		return client;
	}

	public ClientModel getClientByToken(String authToken) {
		ClientModel client = new ClientModel();
		client = ClientDao.getClientByToken(authToken);
		return client;
	}
	
}
