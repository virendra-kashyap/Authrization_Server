package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class ApiClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiClientApplication.class, args);
	}

	private final WebClient webClient;

	public ApiClientApplication(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://localhost:2222").build();
	}

	@GetMapping("/getAccount")
	public Mono<ResponseEntity<String>> getAccount(@RequestHeader("Authorization") String token) {
		return webClient.get().uri("/account").header(HttpHeaders.AUTHORIZATION, token).exchange()
				.flatMap(clientResponse -> clientResponse.toEntity(String.class))
				.map(responseEntity -> new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode()));
	}

}
