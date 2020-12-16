package com.example.demo.model;

import lombok.Value;

import java.util.Date;

/**
 * Customer model. Immutable.
 */
@Value
public class Customer {

    private long id;
    private String name;
    private Date duetime;
    private Date jointime;

}
