package com.gilvam.cursomc;

import com.gilvam.cursomc.domain.*;
import com.gilvam.cursomc.enums.PaymentStatus;
import com.gilvam.cursomc.enums.TypeClient;
import com.gilvam.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ItemOrderRepository itemOrderRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Category cat1 = new Category("information technology");
		Category cat2 = new Category("Escritório");
		Category cat3 = new Category("Cama mesa e banho");
		Category cat4 = new Category("Eletrônicos");
		Category cat5 = new Category("Jardinagem");
		Category cat6 = new Category("Decoração");
		Category cat7 = new Category("Perfumaria");

		Product product1 = new Product("Computer", 2000.00);
		Product product2 = new Product("Printer", 800.00);
		Product product3 = new Product("Mouse", 80.00);

		cat1.getProducts().addAll(Arrays.asList(product1, product2, product3));
		cat2.getProducts().addAll(Arrays.asList(product2));

		product1.getCategories().addAll(Arrays.asList(cat1));
		product2.getCategories().addAll(Arrays.asList(cat1, cat2));
		product3.getCategories().addAll(Arrays.asList(cat1));

		this.categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		this.productRepository.saveAll(Arrays.asList(product1, product2, product3));



		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");
		City city1 = new City(null, "Uberlândia", state1);
		City city2 = new City(null, "Uberlândia", state2);
		City city3 = new City(null, "Campinas", state2);

		state1.getCities().addAll(Arrays.asList(city1));
		state2.getCities().addAll(Arrays.asList(city2, city3));

		this.stateRepository.saveAll(Arrays.asList(state1, state2));
		this.cityRepository.saveAll(Arrays.asList(city1, city2, city3));



		Client client1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", TypeClient.PERSONINDIVIDUAL);
		client1.getPhones().addAll(Arrays.asList("27363323", "9338393"));

		Address address1 = new Address(null, "Rua Flores", 300, "Apto 303", "Jardim", "38220834", client1, city1);
		Address address2 = new Address(null, "Avenida Matos", 105, "Sala 800", "Centro", "38777012", client1, city2);
		client1.getAddresses().addAll(Arrays.asList(address1, address2));

		this.clientRepository.save(client1);
		this.addressRepository.saveAll(Arrays.asList(address1, address2));



		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Order order1 = new Order(null, sdf.parse("30/09/2017 10:32"), client1, address1);
		Order order2 = new Order(null, sdf.parse("10/10/2017 19:35"), client1, address2);

		Payment pay1 = new PaymentCreditCard(null, PaymentStatus.PAID, order1, 6);
		order1.setPayment(pay1);

		Payment pay2 = new PaymentBankSlip(null, PaymentStatus.PENDING, order2, sdf.parse("20/10/2017 00:00"), null);
		order2.setPayment(pay2);

		client1.getOrders().addAll(Arrays.asList(order1, order2));

		orderRepository.saveAll(Arrays.asList(order1, order2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));



		ItemOrder itemOrder1 = new ItemOrder(order1, product1, 0.00, 1, 2000.00);
		ItemOrder itemOrder2 = new ItemOrder(order1, product3, 0.00, 2, 80.00);
		ItemOrder itemOrder3 = new ItemOrder(order2, product2, 100.00, 1, 800.00);

		order1.getItens().addAll(Arrays.asList(itemOrder1, itemOrder2));
		order2.getItens().addAll(Arrays.asList(itemOrder3));

		product1.getItens().addAll(Arrays.asList(itemOrder1));
		product2.getItens().addAll(Arrays.asList(itemOrder3));
		product3.getItens().addAll(Arrays.asList(itemOrder2));

		itemOrderRepository.saveAll(Arrays.asList(itemOrder1, itemOrder2, itemOrder3));
	}
}
