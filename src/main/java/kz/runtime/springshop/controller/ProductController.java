package kz.runtime.springshop.controller;

import kz.runtime.springshop.config.UserDetailsImpl;
import kz.runtime.springshop.config.UserDetailsServiceImpl;
import kz.runtime.springshop.model.Product;
import kz.runtime.springshop.model.Review;
import kz.runtime.springshop.model.User;
import kz.runtime.springshop.repository.ProductRepository;
import kz.runtime.springshop.repository.ReviewRepository;
import kz.runtime.springshop.repository.UserRepository;
import kz.runtime.springshop.service.CategoryService;
import kz.runtime.springshop.service.ProductService;
import kz.runtime.springshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
// localhost:8080/products/{prodId}
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String findAll(@RequestParam(required = false) Double minPrice,
                          @RequestParam(required = false) Double maxPrice,
                          @RequestParam(required = false) Long categoryId,
                          Model model) {
        List<Product> products = productService.findByFilters(minPrice, maxPrice, categoryId);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
//        model.addAttribute("user", userService.getUser());
        return "products";
    }

    @GetMapping("/{productId}")
    public String findById(
            @PathVariable long productId,
            Model model
    ) {
        Product product = productService.findById(productId);
        model.addAttribute("product", product);
        model.addAttribute("options", productService.getOptions(product));
        model.addAttribute("user", userService.getUser());
        return "product_single_page";
    }

    // http://localhost:8080/products/create
    @GetMapping("/create/chooseCategory")
    public String chooseCategory(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "choose_category_for_product";
    }

    // http://localhost:8080/products/create?categoryId=1
    @GetMapping("/create")
    public String showForm(Model model, @RequestParam(required = false) Long categoryId) {
        if (categoryId == null) {
            return "redirect:/products/create/chooseCategory";
        }
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryService.findById(categoryId));
        model.addAttribute("user", userService.getUser());
        return "product_create";
    }

    @GetMapping("/update/{productId}")
    public String updateForm(Model model, @PathVariable long productId) {
        Product product = productService.findById(productId);
        model.addAttribute("product", product);
        model.addAttribute("options", productService.getOptions(product));
        return "product_update";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Product product,
            @RequestParam Long categoryId,
            @RequestParam List<String> valueNames,
            @RequestParam List<Long> optionIds
    ) {
        productService.create(product, categoryId, optionIds, valueNames);
        return "redirect:/products";
    }

    @PostMapping("/update/{productId}")
    public String update(
            @PathVariable long productId,
            @RequestParam String updatedName,
            @RequestParam double updatedPrice,
            @RequestParam List<String> valueNames,
            @RequestParam List<Long> optionIds
    ) {
        productService.update(productId, updatedName, updatedPrice, optionIds, valueNames);
        return "redirect:/products";
    }

    @GetMapping("/delete/{productId}")
    public String deleteById(@PathVariable long productId) {
        productService.deleteById(productId);
        return "redirect:/products";
    }

    @GetMapping("/details/{id}")
    public String getProductPage(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id продукта:" + id));
        model.addAttribute("product", product);
        return "single_product_page";
    }

    @PostMapping("/{productId}/reviews")
    public String addReview(@PathVariable Long productId,
                            @RequestParam String text,
                            @RequestParam int rating) {
        UserDetailsImpl authenticatedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUserDetails == null) {
            throw new IllegalArgumentException("Пользователь не аутентифицирован");
        }

        User user = authenticatedUserDetails.getUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Неверный id продукта:" + productId));

        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setText(text);
        review.setRating(rating);
        review.setPublicationDate(LocalDateTime.now());
        reviewRepository.save(review);
        return "redirect:/products/" + productId;
    }
}