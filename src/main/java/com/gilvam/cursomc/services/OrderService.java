package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.ItemOrder;
import com.gilvam.cursomc.domain.Order;
import com.gilvam.cursomc.domain.PaymentBankSlip;
import com.gilvam.cursomc.enums.PaymentStatus;
import com.gilvam.cursomc.repositories.ItemOrderRepository;
import com.gilvam.cursomc.repositories.OrderRepository;
import com.gilvam.cursomc.repositories.PaymentRepository;
import com.gilvam.cursomc.repositories.ProductRepository;
import com.gilvam.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;

	@Autowired
	private BankSlipService bankSlipService;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private ItemOrderRepository itemOrderRepository;

	public Order find(Integer id) {
		Optional<Order> opt = this.repo.findById(id);
		return opt.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + Order.class.getName()));
	}

	@Transactional
	public Order insert(Order order){
		order.setId(null);
		order.setInstante(new Date());
		order.getPayment().setStatus(PaymentStatus.PENDING);;
		order.getPayment().setOrder(order);

		//se pagamento boleto
		if(order.getPayment() instanceof PaymentBankSlip) {
			PaymentBankSlip paymentBankSlip = (PaymentBankSlip) order.getPayment();
			this.bankSlipService.completePaymentBankSlip(paymentBankSlip, order.getInstante());
		}
		order = repo.save(order);
		this.paymentRepository.save(order.getPayment());

		for (ItemOrder io: order.getItens()){
			io.setDiscount(0.0);
			io.setPrice( this.productService.find(io.getProduct().getId()).getValue());
			io.setOrder(order);
		}
		this.itemOrderRepository.saveAll(order.getItens());
		return order;
	}
}
