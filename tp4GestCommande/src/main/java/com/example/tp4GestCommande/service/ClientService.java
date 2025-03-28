package com.example.tp4GestCommande.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tp4GestCommande.entity.Client;
import com.example.tp4GestCommande.repository.ClientRepository;



@Service
public class ClientService implements ClientItf {
	
	@Autowired
	private ClientRepository repo;


	@Override
	public void signIn(String email, String nom, String prenom, String mdp) {
		Optional<Client> optionalClient= this.findByEmail(email);
		if(optionalClient.isPresent()) {
			
			throw new RuntimeException("Un compte avec cet email existe déja");
		}
		else {
			var client= new Client(email, nom, prenom, mdp);
			repo.save(client);
		}
	
	}


	@Override
	public boolean clientExists(String email) {

	      return repo.findById(email).isPresent();
	}



	@Override
	public Optional<Client> findByEmail(String email) {
		return repo.findById(email);
	}
	
}

