package org.example.services;

import org.example.databases.Product;
import org.example.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ProductService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    private ProductRepository productRepository;
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public ProductService(ProductRepository productRepository, BCryptPasswordEncoder passwordEncoder) {
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<Product> readAll() {
        log.info("Read all products");
        return productRepository.findAll();
    }

    public void create(Product product) {
        productRepository.save(product);
        log.info("New product added");
    }

    public Optional<Product> findById(Long id) {
        log.info("Product found");
        return productRepository.findById(id);
    }

    public boolean existsById(Long id){
        log.info("Test product existence");
        return productRepository.existsById(id);
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
