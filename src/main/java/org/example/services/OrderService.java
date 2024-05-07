package org.example.services;

import org.example.entities.Order;
import org.example.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Iterable<Order> readAll() {
        log.info("Read all orders");
        return orderRepository.findAll();
    }

    public void create(Order order) {
        orderRepository.save(order);
        log.info("New order added");
    }

    public Optional<Order> findById(Long id) {
        log.info("Product found");
        return orderRepository.findById(id);
    }

    public boolean existsById(Long id){
        log.info("Test product existence");
        return orderRepository.existsById(id);
    }


    public Iterable<Order> findOrdersByUserId(long userId) {
        return orderRepository.findAllByUserId(userId);
    }

//
//    public User read(Long id) {
//        log.info("Read user with id = {}", id);
//        return userRepository.getReferenceById(id);
//    }
//
//    public boolean delete(Long id) {
//        log.info("Delete user with id = {}", id);
//        userRepository.deleteById(id);
//        return true;
//    }

}
