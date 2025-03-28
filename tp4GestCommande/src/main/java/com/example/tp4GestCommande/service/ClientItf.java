package com.example.tp4GestCommande.service;

import java.util.Optional;

import com.example.tp4GestCommande.entity.Client;

public interface ClientItf {
	
	
	public void signIn(String email, String nom, String prenom, String mdp);
	
    public boolean clientExists(String email);

	public Optional<Client> findByEmail(String email);
	

    
    
}
