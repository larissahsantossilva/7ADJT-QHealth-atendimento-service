package br.com.fiap.qhealth.ms.atendimento_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "br.com.fiap.qhealth.ms.atendimento_service.external")
@SpringBootApplication
public class AtendimentoServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AtendimentoServiceApplication.class, args);
	}
}
