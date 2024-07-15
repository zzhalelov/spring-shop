package kz.runtime.springshop.service;

import kz.runtime.springshop.model.Order;

import java.util.List;

public interface OrderService {
    void create(String address);

    List<Order> findAllOrders();

    List<Order> findOrdersByUser();
}