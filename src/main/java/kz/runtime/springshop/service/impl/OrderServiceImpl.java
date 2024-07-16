package kz.runtime.springshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.runtime.springshop.model.*;
import kz.runtime.springshop.repository.CartItemRepository;
import kz.runtime.springshop.repository.OrderProductRepository;
import kz.runtime.springshop.repository.OrderRepository;
import kz.runtime.springshop.service.OrderService;
import kz.runtime.springshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;

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

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден"));

        List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(orderId);
        orderProductRepository.deleteAll(orderProducts);

        List<CartItem> cartItems = cartItemRepository.findAllByUserOrderById(order.getUser());
        cartItemRepository.deleteAll(cartItems);

        orderRepository.delete(order);
    }
}