package com.skilleen.orderingservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ShippingOrder implements Serializable {

    private String name;
    private double price;
    private Date datePlaced;

    public ShippingOrder(String name, double price) {
        this.name = name;
        this.price = price;
        this.datePlaced = new Date();

    }
}
