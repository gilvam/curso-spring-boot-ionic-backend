package com.gilvam.cursomc;

import com.gilvam.cursomc.domain.Category;
import com.gilvam.cursomc.repositories.CategoryRepository;
import com.gilvam.cursomc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<Category> categories = new ArrayList<>();
		categories.add(new Category(null, "Informática"));
		categories.add(new Category(null, "Escritório"));

		categoryRepository.saveAll(categories);
	}
}
