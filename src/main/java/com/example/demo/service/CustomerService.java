package com.example.demo.service;

import com.example.demo.model.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;

/**
 * Customer Service. Operation with customers.
 */
@Service
public class CustomerService {

    /**
     * Sort customers ascending by Due Date.
     *
     * @param customers Customer flux.
     * @return Sorted customer flux.
     */
    public Flux<Customer> sortByDueDate(Flux<Customer> customers) {
        return customers.sort(Comparator.comparing(Customer::getDuetime));
    }

}
