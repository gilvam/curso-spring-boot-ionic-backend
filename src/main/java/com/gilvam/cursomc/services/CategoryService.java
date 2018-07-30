package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.Category;
import com.gilvam.cursomc.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;

	public Category find(Integer id){
		Optional<Category> opt = this.repo.findById(id);
		return opt.orElse(null);
	}
}
