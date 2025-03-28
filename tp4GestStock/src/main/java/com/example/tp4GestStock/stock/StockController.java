package com.example.tp4GestStock.stock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/stock")
public class StockController {
	
	@GetMapping("/home")
	public ModelAndView home() {
		
		return new ModelAndView("/stock/home");
	}
}
