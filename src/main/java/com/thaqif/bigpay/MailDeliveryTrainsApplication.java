package com.thaqif.bigpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.thaqif.bigpay.*")
@SpringBootApplication
public class MailDeliveryTrainsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailDeliveryTrainsApplication.class, args);
	}
}
