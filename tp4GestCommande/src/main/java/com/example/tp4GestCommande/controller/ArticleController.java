package com.example.tp4GestCommande.controller;


import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.tp4GestCommande.service.ArticleItf;
import com.example.tp4GestCommande.service.CommandeItf;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/store")
public class ArticleController {

	
	@Autowired 
	private ArticleItf articleService;
	
	@Autowired 
	private CommandeItf commandeService;
	
	
	@PostMapping("/addArticle")
	public RedirectView addArtcile(@RequestParam Long idCommande,
								   @RequestParam String libelle,
								   @RequestParam int quantite, 
								   @RequestParam double prix,
								   RedirectAttributes redirectAttributes
								   ) {
			
			if( libelle.isBlank()) {
				 redirectAttributes.addFlashAttribute("error4", "vous devez entrer un libellé");
					return new RedirectView("/store/viewCommande?idCommande=" + idCommande);

				 }
			
			try {
				articleService.addArticle(idCommande, libelle, quantite, prix);						
				redirectAttributes.addFlashAttribute("success4", "Ajout d'article réussi");
			}
			catch(RuntimeException e){
				redirectAttributes.addFlashAttribute("error4", e.getMessage());
			}
			
			return new RedirectView("/store/listeArticles?idCommande=" + idCommande);
			
	}
	
	
	@GetMapping("/listeArticles")
	public ModelAndView getListArticles(@RequestParam Long idCommande, HttpSession session) {
		String email= (String) session.getAttribute("EmailClient");
				
				if(email==null) {
					
					return new ModelAndView("redirect:/store/home");
		}
		
	    var commande = commandeService.findCommandeById(idCommande).get();
	    var listArticles = commande.getArticles();
	    var model = Map.of("articles", listArticles, "commande", commande); 
	    return new ModelAndView("/store/commande", model);
	    
	}

	
	  @PostMapping("/deleteArticle")
	    public RedirectView deleteArticle(@RequestParam Long idCommande, @RequestParam Long idArticle) {
	        	
	        articleService.deleteArticle(idArticle); 
	        return new RedirectView("/store/listeArticles?idCommande=" + idCommande);
	    }
	  
	  @GetMapping("/printCommande")
	  public ModelAndView printCommande(@RequestParam Long idCommande, HttpSession session) {
		  String email= (String) session.getAttribute("EmailClient");
			
			if(email==null) {
				
				return new ModelAndView("redirect:/store/home");
			}
	      	  var commande = commandeService.findCommandeById(idCommande).get();
	          var listArticles = commande.getArticles();
	          var model = Map.of("articles", listArticles, "commande", commande);
	          
	          return new ModelAndView("/store/facture", model);
	         
	  }

	
}

