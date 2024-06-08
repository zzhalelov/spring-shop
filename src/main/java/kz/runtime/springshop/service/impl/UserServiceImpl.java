package kz.runtime.springshop.service.impl;

import kz.runtime.springshop.model.CartItem;
import kz.runtime.springshop.model.Product;
import kz.runtime.springshop.model.Role;
import kz.runtime.springshop.model.User;
import kz.runtime.springshop.repository.CartItemRepository;
import kz.runtime.springshop.repository.ProductRepository;
import kz.runtime.springshop.repository.UserRepository;
import kz.runtime.springshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    public final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public void create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Override
    public User getUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return userRepository
                .findByLogin(authentication.getName())
                .orElseThrow();
    }

    @Override
    public void addItemToCart(long productId) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(getUser());

        Product product = productRepository.findById(productId).orElseThrow();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);

        cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findAllCartItems() {
        User user = getUser();
        return cartItemRepository.findAllByUser(user);
    }
}