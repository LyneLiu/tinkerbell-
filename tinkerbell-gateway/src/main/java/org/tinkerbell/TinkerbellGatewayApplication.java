package org.tinkerbell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class TinkerbellGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinkerbellGatewayApplication.class, args);
	}
}
