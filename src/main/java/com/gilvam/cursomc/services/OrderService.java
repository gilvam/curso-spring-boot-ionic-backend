package com.gilvam.cursomc.services;

import com.gilvam.cursomc.domain.ItemOrder;
import com.gilvam.cursomc.domain.Order;
import com.gilvam.cursomc.domain.PaymentBankSlip;
import com.gilvam.cursomc.enums.PaymentStatus;
import com.gilvam.cursomc.repositories.*;
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
	private ProductService productService;

	@Autowired
	private ItemOrderRepository itemOrderRepository;

	@Autowired
	private ClientService clientService;

	public Order find(Integer id) {
		Optional<Order> opt = this.repo.findById(id);
		return opt.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + Order.class.getName()));
	}

	@Transactional
	public Order insert(Order order){
		order.setId(null);
		order.setInstante(new Date());
		order.setClient(this.clientService.find(order.getClient().getId()));
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
			io.setProduct(this.productService.find(io.getProduct().getId()));
			io.setPrice(io.getProduct().getValue());
			io.setOrder(order);
		}
		this.itemOrderRepository.saveAll(order.getItens());

		System.out.println(order);

		return order;
	}
}
