package com.gilvam.cursomc.repositories;

import com.gilvam.cursomc.domain.Category;
import com.gilvam.cursomc.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    //pesquisa 1 igual a pesquisa 2
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT ob FROM Product ob INNER JOIN ob.categories cat WHERE ob.name LIKE %:name% AND cat IN :categories")
    Page<Product> search(@Param("name") String name, @Param("categories") List<Category> categoryList, Pageable pageRequest);

    // pesquisa 2
    @Transactional(readOnly = true)
    Page<Product> findDistinctByNameContainingAndCategoriesIn(String name, List<Category> categoryList, Pageable pageRequest);

}
