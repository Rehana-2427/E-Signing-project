package com.example.esigning.gateway.Gateway_E_signing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayESigningApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayESigningApplication.class, args);
	}

}
