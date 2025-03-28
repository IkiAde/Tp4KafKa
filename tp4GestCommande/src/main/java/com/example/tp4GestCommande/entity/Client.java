package com.example.tp4GestCommande.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Client {
	
	private String nom;
	private String prenom;
	private String mdp;
	
	@Id
	private String email;
	
	public String getEmail(){
		
		return email;
	}

	
	public Client(){}
	
	
	
	public Client(String email, String nom, String prenom, String mdp) {
		 this.email= email;
		 this.setNom(nom);
		 this.setPrenom(prenom);
		 this.mdp= mdp;
		 
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getMdp() {
		return mdp;
	}


	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	
	public List<Commande> getCommandes(){
		return this.commandes;
	}
	
	public void addCommande(Commande commande) {
        commandes.add(commande);
       
    }
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Commande> commandes=new ArrayList<>(); 

	

}