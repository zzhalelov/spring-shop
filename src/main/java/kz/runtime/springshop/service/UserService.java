package kz.runtime.springshop.service;

import kz.runtime.springshop.model.CartItem;
import kz.runtime.springshop.model.User;

import java.util.List;

public interface UserService {
    void create(User user);

    User getUser();

    void addItemToCart(long productId);

    List<CartItem> findAllCartItems();
}