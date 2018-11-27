package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.Category;
import com.gilvam.cursomc.domain.Product;
import com.gilvam.cursomc.repositories.CategoryRepository;
import com.gilvam.cursomc.repositories.ProductRepository;
import com.gilvam.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categorieRepository;

    public Product find(Integer id) {
        Optional<Product> opt = this.productRepository.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Product.class.getName()));
    }

    public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Category> categoryList = this.categorieRepository.findAllById(ids);
        return this.productRepository.findDistinctByNameContainingAndCategoriesIn(name, categoryList, pageRequest);
    }
}
