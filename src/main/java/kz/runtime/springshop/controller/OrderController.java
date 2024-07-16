package kz.runtime.springshop.controller;

import kz.runtime.springshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public String showFormPage() {
        return "order_address";
    }

    @PostMapping
    public String create(@RequestParam String address, Model model) {
        orderService.create(address);
        model.addAttribute("message", "Ващ заказ успешно оформлен");
        return "order_success";
    }

    @GetMapping("/list")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.findOrdersByUser());
        return "order_list";
    }

    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders/list";
    }
}