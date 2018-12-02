package com.gilvam.cursomc.resources;

import com.gilvam.cursomc.domain.Category;
import com.gilvam.cursomc.domain.Order;
import com.gilvam.cursomc.dto.CategoryDTO;
import com.gilvam.cursomc.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> find(@PathVariable Integer id) {
        Order order = this.orderService.find(id);
        return ResponseEntity.ok().body(order);
    }


    //necessário criar orderDTO
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Order order) {
        order = this.orderService.insert(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Order>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
                                                @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
                                                @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        Page<Order> list = orderService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}
