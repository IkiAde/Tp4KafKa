package com.example.tp4GestCommande.service;

import java.util.List;

import com.example.tp4GestCommande.entity.Article;



public interface ArticleItf {
	
	
	public void addArticle(Long idcommande, String libelle, int quantite, double prix);
	
	public void deleteArticle(Long idArticle);
	
	public List<Article> getArticleParCommande(Long idCommande);
}
