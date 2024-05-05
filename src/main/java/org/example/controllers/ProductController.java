package org.example.controllers;

import org.example.databases.Product;
import org.example.databases.User;
import org.example.services.ProductService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ProductController {
    private ProductService productService;
    private UserService userService;
    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @Autowired
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @GetMapping("/")
    public String homePage(Model model) {
        log.info("Home page visited");
        return "home";
    }

    @GetMapping("/shop")
    public String shopPage(Model model) {
        log.info("Shop page visited");
        Iterable<Product> products = productService.readAll();
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/account")
    public String accountPage() {
        log.info("account page visited");
        return "account";
    }

    @GetMapping("/shop/add")
    public String addProduct(Model model) {
        log.info("Shop-add page visited");
        return "add-product";
    }

    @PostMapping("/shop/add")
    public String addPostProduct(@RequestParam String name,
                                 @RequestParam String description,
                                 @RequestParam int price,
                                 Model model) {
        log.info("Shop-add post request");
        Product product = new Product(name, description, price);
        productService.create(product);
        return "redirect:/shop";
    }

    @GetMapping("/buy/{id}")
    public String buyProduct(@PathVariable(value = "id") long id, Model model) {
        log.info("Buy page visited");
        if (!productService.existsById(id)) {
            return "redirect:/shop";
        }
        Optional<Product> product = productService.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user_buyer", user);
        return "buy-details";
    }

}
