package com.example.tp4GestCommande.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.tp4GestCommande.entity.Article;
import com.example.tp4GestCommande.entity.Commande;
import com.example.tp4GestCommande.service.CommandeService;
import com.example.tp4GestCommande.service.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/store")
public class CommandeController {
	
	@Autowired
	private CommandeService commandeService;
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
	@PostMapping("/sendCommande")
	public RedirectView envoyerCommande(@RequestParam Long idCommande, HttpSession session, RedirectAttributes redirectAttributes) {
	    String email = (String) session.getAttribute("EmailClient");
	    if (email == null) {
	        return new RedirectView("/store/home");
	    }
		Optional<Commande> optCommande= commandeService.findCommandeById(idCommande);
		
		if(optCommande.isPresent()) {
			
			Commande commande= optCommande.get();
			
			
			commande.getArticles();
	        String commandeJson = convertCommandeToJson(commande.getArticles());
	        
	        kafkaProducer.produce(commandeJson);
	        
	        redirectAttributes.addFlashAttribute("success5", "Commande envoyée au stock.");
			return new RedirectView("/store/viewCommande?idCommande=" + idCommande);
		}
	    
		else{
	        redirectAttributes.addFlashAttribute("error5", "Commande introuvable.");
	        return new RedirectView("/store/compte");
	    }
	}

	
	private String convertCommandeToJson(List<Article> list) {
		for(var prd : list) {
			System.out.println("LA LISTE"+prd);
		}
	    ObjectMapper objectMapper = new ObjectMapper();
	    return "gfhgf";
		//return objectMapper.writeValueAsString(list);
	}


}
