package com.example.tp4GestStock.stock;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;



@Controller
@RequestMapping("/stock")
public class StockController {
	
	@Autowired
	private StockService stockService;
	
	@GetMapping("/home")
	public ModelAndView home(Model model) {
	    model.addAttribute("stock", stockService.getStock()); 
	    return new ModelAndView("stock/home", model.asMap()); 
	}


	
	@PostMapping("/refill")
	public RedirectView refill() {
	    stockService.refillStock();
	    return new RedirectView("/stock/home");
	}

}
