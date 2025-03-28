package com.example.tp4GestCommande.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.tp4GestCommande.entity.Commande;


public interface CommandeRepository extends CrudRepository<Commande, Long>{
	List<Commande> findAll();
	

}
