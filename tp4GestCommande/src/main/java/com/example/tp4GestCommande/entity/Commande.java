package com.example.tp4GestCommande.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;



@Entity
public class Commande {
	
	
	private String titre;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommande;
	
	public Long getIdCommande() {
        return idCommande;
    }
	public Commande(){}	
	
	public Commande(String titre) {
		this.titre=titre;
	}
	
	
	public String getTitre() {
		return titre;
	}
	

	@ManyToOne
	(cascade=CascadeType.PERSIST)
	Client client;
	
	public void setClient(Client client) {
		this.client= client;
	}
	
	public Client getClient() {
		return client;
	}

	public List<Article> getArticles() {
		return articles;
	}


	@OneToMany(mappedBy="commande", cascade= CascadeType.ALL)
	private List<Article> articles= new ArrayList<>();

}
