package kz.runtime.springshop.service.impl;

import kz.runtime.springshop.model.*;
import kz.runtime.springshop.repository.OrderProductRepository;
import kz.runtime.springshop.repository.OrderRepository;
import kz.runtime.springshop.service.OrderService;
import kz.runtime.springshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserService userService;

    @Override
    public void create(String address) {
        User user = userService.getUser();

        Order order = new Order();
        order.setAddress(address);
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);
        order.setCreated(LocalDateTime.now());
        orderRepository.save(order);

        List<CartItem> cartItems = user.getCartItems();
        for (CartItem cartItem : cartItems) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(cartItem.getProduct());
            orderProduct.setQuantity(cartItem.getQuantity());
            orderProductRepository.save(orderProduct);
        }
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findOrdersByUser() {
        User user = userService.getUser();
        return orderRepository.findAllByUser(user);
    }
}