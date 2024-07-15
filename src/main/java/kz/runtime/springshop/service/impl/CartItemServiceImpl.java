package kz.runtime.springshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.runtime.springshop.model.CartItem;
import kz.runtime.springshop.model.Product;
import kz.runtime.springshop.model.User;
import kz.runtime.springshop.repository.CartItemRepository;
import kz.runtime.springshop.repository.ProductRepository;
import kz.runtime.springshop.service.CartItemService;
import kz.runtime.springshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;

    @Override
    public void addItemToCart(long productId) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(userService.getUser());

        Product product = productRepository.findById(productId).orElseThrow();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void increaseAmount(long cartItemId) {
        CartItem cartItem = getById(cartItemId);
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void decreaseAmount(long cartItemId) {
        CartItem cartItem = getById(cartItemId);
        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    public List<CartItem> findAllCartItems() {
        User user = userService.getUser();
        return cartItemRepository.findAllByUserOrderById(user);
    }

    @Override
    public void deleteCartItem(long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    private CartItem getById(long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Сущность не найдена"));
    }
}