package com.example.tp4GestCommande.controller;



import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.tp4GestCommande.entity.Client;
import com.example.tp4GestCommande.entity.Commande;
import com.example.tp4GestCommande.service.ClientItf;
import com.example.tp4GestCommande.service.CommandeService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/store")
public class ClientController {
	
	@Autowired
	private ClientItf serviceClient;
	
	@Autowired
	private CommandeService commandeService;

	
	@PostMapping("/signIn")
	public RedirectView signIn(@RequestParam String email,
							   @RequestParam String nom, 
							   @RequestParam String prenom,
							   @RequestParam String mdp,
							   RedirectAttributes redirectAttributes) {
		
		if (email.isBlank() || nom.isBlank() || prenom.isBlank() || mdp.isBlank()) {
	        redirectAttributes.addFlashAttribute("error1", "Tous les champs doivent être remplis");
	        return new RedirectView("/store/home");
	    }
		
		try {
			serviceClient.signIn(email, nom, prenom, mdp);
			redirectAttributes.addFlashAttribute("success1","Inscription réussie");
		}
		catch(RuntimeException e){
			redirectAttributes.addFlashAttribute("error1", e.getMessage());
		}
		
		return new RedirectView("/store/home");
	}
	
	
	@PostMapping("/login")
	public ModelAndView login(@RequestParam String email,
							  @RequestParam String mdp,
							  Model model,
							  HttpSession session) {
		
		var client =  serviceClient.findByEmail(email);
		
		if ((serviceClient.clientExists(email)) && (client.get().getMdp().equals(mdp))) {
			session.setAttribute("EmailClient", client.get().getEmail());
			session.setAttribute("prenomClient", client.get().getPrenom());
			model.addAttribute("EmailClient", client.get().getEmail());
			return new ModelAndView("redirect:/store/compte");
		}
			
		else{
			model.addAttribute("error2", "mauvais mdp");
			return new ModelAndView("/store/home");
		}
		
	}
	
	
	@PostMapping("/logout")
	public RedirectView logout(HttpSession session ) {
		session.invalidate();
		
		return new  RedirectView("/store/home");
	}
	
	@GetMapping("/home")
	public ModelAndView home() {
		
		return new ModelAndView("/store/home");
	}
	
	@GetMapping("/compte")
	public ModelAndView compte(Model model, HttpSession session) {
		String email= (String) session.getAttribute("EmailClient");
		if(email==null) {
			
			return new ModelAndView("redirect:/store/home");
		}
		else {
			Optional<Client> optClient= serviceClient.findByEmail(email);
			if(optClient.isPresent()) {
				var client= optClient.get();
				List<Commande> listCommandes= commandeService.getCommandesParClient(email);
				var commandes= Map.of("commandes", listCommandes, "EmailClient", email);
				return new ModelAndView("/store/compte",commandes);
			}
			else {
				return new ModelAndView("redirect:/store/compte");
			}
			
		}
		
	}
	
	
	
	@PostMapping("/addCommande")
	public RedirectView addCommande(@RequestParam String titre,HttpSession session,RedirectAttributes redirectAttributes) {
		String email= (String) session.getAttribute("EmailClient");
		 if(email==null) {
			
			 redirectAttributes.addFlashAttribute("error3", "Vous n'êtes pas connecté");
		 }
		 else {
			 if(titre.isBlank()) {
				 
				 redirectAttributes.addFlashAttribute("error3", "Vous devez entrer un nom de commande");
			 }
			 else {
			    commandeService.createCommande(email, titre);
			    redirectAttributes.addFlashAttribute("success3", "Ajout réussi");
			 }
		 }
		 		
		return new RedirectView("/store/liste");	
	}
	
	@GetMapping("/liste")
	public ModelAndView getListCommandes(HttpSession session,Model model) {
		String email= (String) session.getAttribute("EmailClient");
		
		if(email==null) {
			
			return new ModelAndView("redirect:/store/home");
		}
		
		else {
			var listCommandes= commandeService.getCommandesParClient(email);
			
			var commandes= Map.of("commandes", listCommandes, "EmailClient", email);
			
			return new ModelAndView("/store/compte",commandes);
		}
	}

	@GetMapping("/viewCommande")
	public ModelAndView detailsCommande(@RequestParam Long idCommande, Model model, HttpSession session){
		String email= (String) session.getAttribute("EmailClient");
		if (email==null) {
			return new ModelAndView ("redirect:/store/home");
		}
		else {
			Optional<Commande> optCommande= commandeService.findCommandeById(idCommande);
			if(optCommande.isPresent()) {
				var commande= optCommande.get();
				model.addAttribute("commande", commande);
				return new ModelAndView("/store/commande");
			}
			else {
				return new ModelAndView("redirect:/store/compte");
			}
		}
		
	}
	
}
