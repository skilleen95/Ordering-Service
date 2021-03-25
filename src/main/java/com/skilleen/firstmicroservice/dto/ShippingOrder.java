package com.skilleen.firstmicroservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ShippingOrder {

    private String name;
    private double price;
    private Date dateShipped;
    private String shippingFromLocation;

    public ShippingOrder(String name, double price) {
        this.name = name;
        this.price = price;
        this.dateShipped = new Date();
        this.shippingFromLocation = "";

    }
}
