package com.example.tp4GestCommande.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tp4GestCommande.entity.Client;
import com.example.tp4GestCommande.entity.Commande;
import com.example.tp4GestCommande.repository.ClientRepository;
import com.example.tp4GestCommande.repository.CommandeRepository;;


@Service
public class CommandeService implements CommandeItf {

	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private CommandeRepository commandeRepo;

	@Override
	public List<Commande> getCommandesParClient(String emailClient) {
			
		    Optional<Client> optionalClient= clientRepo.findById(emailClient);
		    if(optionalClient.isPresent()) {
		    	var client= optionalClient.get();
		    	List<Commande> commandes= client.getCommandes();
		    	return commandes;
		    }
		    else {
		    	throw new RuntimeException("erreur de client");
		    }
		    
	    	
			
		
		 
	}

	@Override
	public void createCommande(String emailClient, 	String titre) {
		Optional<Client> optionalClient= clientRepo.findById(emailClient);
		
		if(optionalClient.isPresent()) {
			var client= optionalClient.get();
			var commande= new Commande(titre);
			commande.setClient(client);
			commandeRepo.save(commande);
		}
		
	}

	@Override
	public Optional<Commande> findCommandeById(Long id) {
		
		return commandeRepo.findById(id);
	}


	
	



}
