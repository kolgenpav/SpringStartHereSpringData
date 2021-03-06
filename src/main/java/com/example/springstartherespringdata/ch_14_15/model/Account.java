package com.example.springstartherespringdata.ch_14_15.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class Account {
    @Id         //mark field mapped as primary key
    private long id;
    private String name;
    private BigDecimal amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
