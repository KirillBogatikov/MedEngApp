package ru.medeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.medeng.app.db.AuthRepository;
import ru.medeng.app.db.CustomerRepository;
import ru.medeng.app.db.OperationRepository;
import ru.medeng.app.db.OrderRepository;
import ru.medeng.app.db.ProductRepository;
import ru.medeng.app.db.migrations.Migrator;
import ru.medeng.configuration.SecurityConfig;
import ru.medeng.domain.AuthService;
import ru.medeng.domain.CustomerService;
import ru.medeng.domain.OrderService;
import ru.medeng.domain.ProductService;
import ru.medeng.domain.ShipmentService;
import ru.medeng.tools.Hash;
import ru.medeng.tools.Resources;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Configuration
@EnableSwagger2
public class Launcher {
	private static String jdbcURL;
	private static SecurityConfig security;
	private AuthService auth;
	private ProductService product;
	private CustomerService customer;
	private ShipmentService shipment;
	private OrderService order;
	
	public Launcher() {
		
	}
	
	@Bean
	public AuthService auth() {
		if (auth == null) {
			auth = new AuthService(security, new AuthRepository(jdbcURL));
		}
		
		return auth;
	}
	
	@Bean
	public ProductService product() {
		if (product == null) {
			product = new ProductService(new ProductRepository(jdbcURL));
		}
		return product;
	}
	
	@Bean
	public CustomerService customer() {
		if (customer == null) {
			customer = new CustomerService(new CustomerRepository(jdbcURL));
		}
		return customer;
	}
	
	@Bean
	public ShipmentService shipment() {
		if (shipment == null) {
			shipment = new ShipmentService(new OperationRepository(jdbcURL));
		}
		return shipment;
	}
	
	@Bean
	public OrderService order() {
		if (order == null) {
			order = new OrderService(new OrderRepository(jdbcURL), new OperationRepository(jdbcURL), new ProductRepository(jdbcURL));
		}
		return order;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis((each) -> true)
				.paths((each) -> each.startsWith("/api/")).build();
	}

	public static void main(String... args) {
		Resources.init(Launcher.class);
		
		var security = new SecurityConfig();
		security.setSalt(System.getenv("SECURITY_SALT"));
		security.setSaltIndex(Integer.valueOf(System.getenv("SECURITY_SALT_INDEX")));
		security.setSecret(System.getenv("SECURITY_SECRET"));
		security.setTokenLifeTime(Long.valueOf(System.getenv("SECURITY_TOKEN_LIFE_TIME")));
		
		Hash.init(security.getSalt(), security.getSaltIndex());

		Launcher.security = security;
		Launcher.jdbcURL = System.getenv("PG_DATABASE_URL");
		
		var thread = new Thread(() -> {
			try {
				var migrator = new Migrator(Launcher.jdbcURL);
			} catch(Exception e) {
				e.printStackTrace();
			}			
		});
		thread.start();
		
		SpringApplication.run(Launcher.class, args);
	}
}
