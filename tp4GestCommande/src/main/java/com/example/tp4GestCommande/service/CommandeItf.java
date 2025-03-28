package com.example.tp4GestCommande.service;

import java.util.List;
import java.util.Optional;

import com.example.tp4GestCommande.entity.Commande;

public interface CommandeItf {
	
	  public void createCommande(String emailClient, String titre);
	  
	  public List<Commande> getCommandesParClient(String emailClient);
	  
	  public Optional<Commande> findCommandeById(Long id);
	}


