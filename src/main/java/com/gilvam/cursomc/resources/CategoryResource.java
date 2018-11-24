package com.gilvam.cursomc.resources;

import com.gilvam.cursomc.domain.Category;
import com.gilvam.cursomc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> find(@PathVariable Integer id) {
        Category category = this.categoryService.find(id);
        return ResponseEntity.ok().body(category);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(Category category) {
        category = this.categoryService.insert(category);

        /** HTTP status code, deve retornar:
         *      - status 201 (inserindo novo recurso)
         *      - fornecer URI
         */
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Category category, @PathVariable Integer id) {
        category.setId(id);
        category = this.categoryService.update(category);
        return ResponseEntity.noContent().build();
    }
}
