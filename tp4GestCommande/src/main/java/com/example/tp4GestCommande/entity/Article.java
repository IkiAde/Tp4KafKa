package com.example.tp4GestCommande.entity;



import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Article {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticle;
	
	
	public Long getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(Long idArticle) {
		this.idArticle = idArticle;
	}


	private String libelle;
	private int quantite;
	private double prixUnitaire;
	
	
	public Article() {}
	
	public Article(String libelle, int quantite, double prix) {
		this.libelle= libelle;
		this.quantite= quantite;
		this.prixUnitaire= prix;
	}
	
	
	
	
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public double getPrixUnitaire() {
		return prixUnitaire;
	}
	public void setPrixUnitaire(double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	
	@ManyToOne
	(cascade=CascadeType.PERSIST)
	@JsonBackReference
	Commande commande;
	
	
	public void setCommande(Commande commande) {
		this.commande=commande;
	}
	
	public Commande getCommande() {
		return this.commande;
	}
}
