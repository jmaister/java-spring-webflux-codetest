package com.example.demo.service;

import com.example.demo.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ControllerServiceTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    private Customer[] sorted;

    @Before
    public void setup() throws IOException {
        sorted = objectMapper.readValue(new File("src/test/resources/customers/sorted.json"), Customer[].class);
    }

    @Test
    public void testSorted() throws IOException {
        // Given
        Customer[] unsorted = objectMapper.readValue(new File("src/test/resources/customers/unsorted.json"), Customer[].class);

        // On
        Flux<Customer> result = customerService.sortByDueDate(Flux.fromArray(unsorted));

        // Then
        StepVerifier.create(result.log())
                .expectNext(sorted)
                .expectComplete()
                .verify();
    }

    @Test
    public void testEmptyList() throws IOException {
        // Given
        Flux<Customer> empty = Flux.empty();

        // On
        Flux<Customer> result = customerService.sortByDueDate(empty);

        // Then
        StepVerifier.create(result.log())
                .expectNext(new Customer[]{})
                .expectComplete()
                .verify();
    }

    @Test
    public void testWrongData() throws IOException {
        // Given
        Customer[] wrongData = objectMapper.readValue(new File("src/test/resources/customers/wrongdata.json"), Customer[].class);

        // On
        Flux<Customer> result = customerService.sortByDueDate(Flux.fromArray(wrongData));

        // Then
        StepVerifier.create(result.log())
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
}
