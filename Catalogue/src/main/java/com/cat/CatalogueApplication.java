package com.cat;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cat.DAO.ProduitRepository;
import com.cat.entities.Produit;


/**
 * 
 * @author jebel haythem
 *
 */


@SpringBootApplication
public class CatalogueApplication {

	public static void main(String[] args) {
		ApplicationContext conx = SpringApplication.run(CatalogueApplication.class, args);
		ProduitRepository prodDao = conx.getBean(ProduitRepository.class);
		//prodDao.save(new Produit("asus", 1500.0, 200));
		//prodDao.save(new Produit("acer", 800.50, 136));
		//prodDao.save(new Produit("lenovo", 1000.500, 60));

	/*	List<Produit> prods = prodDao.findAll();
		for (Produit produit : prods) {
			System.out.println("designation : " + produit.getDesignation());
			System.out.println("prix : " + produit.getPrix());
		}*/
	}

}
