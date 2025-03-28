package com.example.tp4GestCommande.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tp4GestCommande.entity.Article;
import com.example.tp4GestCommande.entity.Commande;
import com.example.tp4GestCommande.repository.ArticleRepository;
import com.example.tp4GestCommande.repository.CommandeRepository;


@Service
public class ArticleService implements ArticleItf {

	@Autowired
	private CommandeRepository commandeRepo;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Override
	public void addArticle(Long idCommande, String libelle, int quantite, double prix) {
		Optional<Commande> optionalCommande= commandeRepo.findById(idCommande);
		if(optionalCommande.isPresent()) {
			var commande= optionalCommande.get();
			var article= new Article(libelle, quantite, prix);
			article.setCommande(commande);
			articleRepo.save(article);	
		}
	}

	@Override
	public void deleteArticle(Long idArticle) {
		articleRepo.deleteById(idArticle);
	}

	@Override
	public List<Article> getArticleParCommande(Long idCommande) {
		Optional <Commande> optCommande= commandeRepo.findById(idCommande);
		if(optCommande.isPresent()) {
			var commande= optCommande.get();
			List<Article> articles= commande.getArticles();
			return articles;
		}
		else {
	    	throw new RuntimeException("Une erreur s'est produite ");
	    }
	}

}
