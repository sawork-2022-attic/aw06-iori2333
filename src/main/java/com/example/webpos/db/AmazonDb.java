package com.example.webpos.db;

import com.example.webpos.model.Product;
import com.example.webpos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AmazonDb implements PosDB {
    ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(cacheNames = "products")
    public List<Product> getProducts() {
        var products = new ArrayList<Product>();
        productRepository.findAll().forEach(products::add);
        return products.stream()
            .filter(product -> product.getPrice() > 0)
            .collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "product")
    public Product getProduct(String productId) {
        return productRepository.findByAsin(productId).orElse(null);
    }
}
