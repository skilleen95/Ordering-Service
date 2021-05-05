package com.skilleen.orderingservice.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity(name = "Order")
@Data
@Builder
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long orderId;

    private String itemName;

    private double price;

    private Long customerId;

    @CreationTimestamp
    private Date createdAt;
}
