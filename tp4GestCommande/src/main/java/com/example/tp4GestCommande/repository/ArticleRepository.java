package com.example.tp4GestCommande.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.tp4GestCommande.entity.Article;
import com.example.tp4GestCommande.entity.Commande;

public interface ArticleRepository  extends CrudRepository<Article, Long>{
	List<Article> findByCommande(Commande commande);
}
