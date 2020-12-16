package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.ZonedDateTime;

/**
 * Customer model. Immutable.
 */
@Value
public class Customer {

    private long id;
    private String name;
    private ZonedDateTime duetime;
    private ZonedDateTime jointime;

}
