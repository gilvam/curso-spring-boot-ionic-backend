package com.gilvam.cursomc.resources;

import com.gilvam.cursomc.domain.Category;
import com.gilvam.cursomc.domain.Product;
import com.gilvam.cursomc.dto.CategoryDTO;
import com.gilvam.cursomc.dto.ProductDTO;
import com.gilvam.cursomc.resources.utils.URL;
import com.gilvam.cursomc.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> find(@PathVariable Integer id) {
		Product order = this.productService.find(id);
		return ResponseEntity.ok().body(order);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProductDTO>> findPage(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categories", defaultValue = "") String categories,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction)
	{
		String nameDecoded = URL.decodeParam(name);
		List<Integer> ids = URL.decodeIntList(categories);

		Page<Product> categoryPage = this.productService.search(nameDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProductDTO> categoryDtoPage = categoryPage.map(item -> new ProductDTO(item));
		return ResponseEntity.ok().body(categoryDtoPage);
	}
}
