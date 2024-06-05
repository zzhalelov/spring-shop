package kz.runtime.springshop.controller;

import kz.runtime.springshop.model.Category;
import kz.runtime.springshop.service.CategoryService;
import kz.runtime.springshop.service.OptionService;
import kz.runtime.springshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final OptionService optionService;
    private final UserService userService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("user", userService.getUser());
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    @GetMapping("/create")
    public String showForm(Model model) {
        model.addAttribute("category", new Category());
        return "category_create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Category category, @RequestParam String optionNames) {
        categoryService.create(category);
        optionService.create(optionNames, category);
        return "redirect:/categories";
    }
}