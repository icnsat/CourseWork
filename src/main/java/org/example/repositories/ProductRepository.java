package org.example.repositories;

import org.example.databases.Product;
import org.example.databases.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    User findByName(String username);
}