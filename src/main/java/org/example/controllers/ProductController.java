package org.example.controllers;

import org.example.entities.*;
import org.example.services.OrderItemService;
import org.example.services.OrderService;
import org.example.services.ProductService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;
    private UserService userService;
    private OrderService orderService;
    private OrderItemService orderItemService;
    private NumberOfItems numberOfItems = new NumberOfItems(1);

    @Autowired
    public ProductController(ProductService productService, UserService userService, OrderService orderService, OrderItemService orderItemService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
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
        model.addAttribute("numberOfItems", numberOfItems);
        model.addAttribute("products", products);
        log.info("количество товаров: {}", numberOfItems.getNumber());
        return "shop";
    }

    @GetMapping("/account")
    public String accountPage(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("account page visited");
        Long id = customUserDetails.getUser().getId();
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("orders", orderService.findOrdersByUserId(id));
        model.addAttribute("numberOfItems", numberOfItems);
        log.info("account page uploaded");
        return "account";
    }


    //для админа//
    @GetMapping("/shop/add")
    public String addProduct(Model model) {
        log.info("Shop-add page visited");
        return "add-product";
    }

    //для админа//
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


    @PostMapping("/add-order")
    public String addNewOrder(@ModelAttribute Order order, @ModelAttribute OrderItem orderItems,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        order.setOrderDate(currentDate.format(formatter));
        order.setUser(customUserDetails.getUser());
        order.setStatus("оформлен");
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemService.saveOrderItem(orderItem);
        }
        orderService.create(order);
        return "redirect:account";
    }

    @GetMapping("/shopping-cart")
    public String getShoppingCart(Model model) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        order.setOrderItems(orderItems);
        for (int i = 0; i < numberOfItems.getNumber(); i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(new Product());
            order.addOrderItem(orderItem);
        }
        model.addAttribute("numberOfItems", numberOfItems);
        model.addAttribute("order", order);

        log.info("{} !!!!!!!",numberOfItems.getNumber());

        return "shopping-cart";
    }

    @PostMapping("/shopping-cart-post")
    public String getNumberOfItems(/*@ModelAttribute*/ NumberOfItems numberOfItems) {
        this.numberOfItems = numberOfItems;
        log.info("количество товаров: {}", this.numberOfItems.getNumber());
        return "redirect:/shop";
    }


   /* @GetMapping("/buy/{id}")
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

    @PostMapping("/buy/{id}")
    public String buyPostProduct(@PathVariable(value = "id") long id, Model model) {
        log.info("Buy page post request");
//        if (!productService.existsById(id)) {
//            return "redirect:/shop";
//        }
        //Optional<Product> order = productService.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        //product.ifPresent(res::add);
        model.addAttribute("product", res);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user_buyer", user);
        return "buy-details";
    }*/

}
