/*package org.example.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "buckets")
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "buckets_products",
            joinColumns = @JoinColumn(name="bucket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id") )
    private List<Product> product;
}
*/