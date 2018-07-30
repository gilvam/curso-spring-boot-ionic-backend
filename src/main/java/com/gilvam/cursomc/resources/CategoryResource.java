package com.gilvam.cursomc.resources;

import com.gilvam.cursomc.domain.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/categories")
public class CategoryResource {

//	@RequestMapping(method = RequestMethod.GET)
//	public String List(){
//		return "Rest 200 ok";
//	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Category> List(){

		Category cat1 = new Category(1, "informática");
		Category cat2 = new Category(1, "Escritório");

		List<Category> list = new ArrayList<>();
		list.add(cat1);
		list.add(cat2);

		return list;
	}
}
