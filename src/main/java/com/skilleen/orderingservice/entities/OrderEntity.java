package com.skilleen.orderingservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity(name = "CustomerOrder")
@Data
@Builder
@AllArgsConstructor
public class OrderEntity {

    public OrderEntity() {}

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long orderId;

    private String itemName;

    private double price;

    private Long customerId;

    @CreationTimestamp
    private Date createdAt;
}
