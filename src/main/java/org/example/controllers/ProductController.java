package org.example.controllers;

import org.example.databases.Product;
import org.example.services.ProductService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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

   /* @GetMapping("/shop")
    public String shopPage(Model model) {
        log.info("Shop page visited");
        Iterable<Product> products = productService.readAll();
        model.addAttribute("products", products);
        return "shop";
    }*/

}
