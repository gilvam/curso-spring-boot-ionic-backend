package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.Category;
import com.gilvam.cursomc.repositories.CategoryRepository;
import com.gilvam.cursomc.services.exceptions.DataIntegrityException;
import com.gilvam.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;

	public Category find(Integer id) {
		Optional<Category> opt = this.repo.findById(id);
		return opt.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + Category.class.getName()));
	}

	public Category insert(Category category) {
		category.setId(null);
		return this.repo.save(category);
	}

	public Category update (Category category) {
		this.find(category.getId());
		return this.repo.save(category);
	}

	public void delete (Integer id) {
		find(id);
		try{
			this.repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
}
