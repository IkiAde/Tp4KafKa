package com.example.tp4GestStock.stock;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class StockService {
	
	public StockService() {
		if (stock.isEmpty()) {
            refillStock();
        }
	}
	
	
	private final Map<String, Integer> stock = new HashMap<>();

    public Map<String, Integer> getStock() {
        return stock;
    }
    
    public void addStock(String libelle, int quantite) {
        stock.put(libelle, stock.getOrDefault(libelle, 0) + quantite);
    }

    public void updateStock(String libelle, int quantite) {
        addStock(libelle, -quantite);
    }
    
    public void refillStock() {
    	addStock("Table", 10);
    	addStock("Chaise", 15);
    	addStock("Fauteuil", 14);
    }

}
