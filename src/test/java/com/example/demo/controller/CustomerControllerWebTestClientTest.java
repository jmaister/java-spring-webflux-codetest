package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerWebTestClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CustomerController controller;

    @Test
    public void testSortCustomers() throws IOException {

        final ObjectMapper objectMapper = new ObjectMapper();
        final Customer[] customers = objectMapper.readValue(new File("customers.json"), Customer[].class);

        webTestClient.post()
                .uri("/customers/sort")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Flux.fromArray(customers), Customer.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Customer.class);
    }

}
