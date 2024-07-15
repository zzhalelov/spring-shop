package kz.runtime.springshop.service;

import kz.runtime.springshop.model.CartItem;

import java.util.List;

public interface CartItemService {
    List<CartItem> findAllCartItems();

    void addItemToCart(long productId);

    void increaseAmount(long productId);

    void decreaseAmount(long productId);

    void deleteCartItem(long cartItemId);
}