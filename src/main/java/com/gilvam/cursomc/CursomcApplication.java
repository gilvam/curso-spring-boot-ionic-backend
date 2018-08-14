package com.gilvam.cursomc;

import com.gilvam.cursomc.domain.Category;
import com.gilvam.cursomc.domain.City;
import com.gilvam.cursomc.domain.Product;
import com.gilvam.cursomc.domain.State;
import com.gilvam.cursomc.repositories.CategoryRepository;
import com.gilvam.cursomc.repositories.CityRepository;
import com.gilvam.cursomc.repositories.ProductRepository;
import com.gilvam.cursomc.repositories.StateRepository;
import com.gilvam.cursomc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		Category cat1 = new Category("information technology");
		Category cat2 = new Category("Office");

		Product p1 = new Product("Computer", 2000.00);
		Product p2 = new Product("Printer", 800.00);
		Product p3 = new Product("Mouse", 80.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));

		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));

		this.categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		this.productRepository.saveAll(Arrays.asList(p1, p2, p3));



		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");
		City city1 = new City(null, "Uberlândia", state1);
		City city2 = new City(null, "Uberlândia", state2);
		City city3 = new City(null, "Campinas", state2);

		//state1.getCities().addAll(Arrays.asList(city1));
		//state2.getCities().addAll(Arrays.asList(city2, city3));

		this.stateRepository.saveAll(Arrays.asList(state1, state2));
		this.cityRepository.saveAll(Arrays.asList(city1, city2, city3));

	}
}
