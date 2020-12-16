package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerWebClientTest {

    @LocalServerPort
    private int randomServerPort;

    @Test
    public void testSortCustomers() throws IOException {
        WebClient webClient = WebClient.create("http://localhost:" + randomServerPort);

        final ObjectMapper objectMapper = new ObjectMapper();
        final Customer[] customers = objectMapper.readValue(new File("customers.json"), Customer[].class);

        Flux<Customer> responseFlux = webClient
                .post()
                .uri("/customers/sort")
                .body(Flux.fromArray(customers), Customer.class)
                .retrieve()
                .bodyToFlux(Customer.class);

    }
}
