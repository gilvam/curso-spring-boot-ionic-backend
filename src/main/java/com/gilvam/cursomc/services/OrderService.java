package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.Order;
import com.gilvam.cursomc.repositories.OrderRepository;
import com.gilvam.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;

	public Order find(Integer id) {
		Optional<Order> opt = this.repo.findById(id);
		return opt.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + Order.class.getName()));
	}
}
