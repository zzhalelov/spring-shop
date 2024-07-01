package kz.runtime.springshop.controller;

import kz.runtime.springshop.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartItemService cartItemService;

    @GetMapping
    public String getCartPage(Model model) {
        double total = cartItemService.findAllCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("cartItems", cartItemService.findAllCartItems());
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/{productId}")
    public String addItemToCart(@PathVariable long productId) {
        cartItemService.addItemToCart(productId);
        return "redirect:/cart";
    }

    @GetMapping("/{id}/increase")
    public String increaseAmount(@PathVariable long id) {
        cartItemService.increaseAmount(id);
        return "redirect:/cart";
    }

    @GetMapping("{id}/decrease")
    public String decreaseAmount(@PathVariable long id) {
        cartItemService.decreaseAmount(id);
        return "redirect:/cart";
    }
}