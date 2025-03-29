package com.example.tp4GestStock.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class KafkaConsumer {
	
	@Autowired
	private  StockService stockService;

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private  final ObjectMapper objectMapper;
    
    
    @Autowired
    public KafkaConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @KafkaListener(topics = "ma_commande", groupId = "my-stock-group")
    public void consume(ConsumerRecord<String, String> record) {
        LOGGER.info("Consumed message: {}", record.value());
        try {
	        JsonNode jsonCommande = objectMapper.readTree(record.value());
	        JsonNode articles = jsonCommande.get("articles");
	       
	        for (JsonNode article : articles) {
	            String libelle = article.get("libelle").asText();
	            int quantite = article.get("quantite").asInt();
	            
	            stockService.updateStock(libelle, quantite);
	            System.out.println("Stock mis à jour pour " + libelle + " : -" + quantite);
	        }
        }
     
    	catch (Exception e) {
        System.err.println("Erreur lors du traitement du message Kafka : " + e.getMessage());
    	}

    
  }

   
}