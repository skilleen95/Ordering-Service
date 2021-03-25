package com.skilleen.orderingservice.services;

import com.skilleen.orderingservice.dto.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final List<Order> list = new ArrayList<>();

    @PostConstruct
    public void initDB() {
        list.add(new Order(67, "Phone", 2000));
        list.add(new Order(68, "Book", 18));
        list.add(new Order(69, "Coffee", 12));
        list.add(new Order(70, "Shoes", 130));
    }

    public void addOrder(Order order) {
        list.add(order);
    }

    public List<Order> getOrders() {
        return list;
    }
}
