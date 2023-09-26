package com.dqt.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ShopForEveryHomeApplication {

	public static void main(String[] args) {
		log.info("Hello App!");
		SpringApplication.run(ShopForEveryHomeApplication.class, args);
	}

}
