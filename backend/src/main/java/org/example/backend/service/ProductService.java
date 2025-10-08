package org.example.backend.service;

import org.example.backend.model.Product;
import org.example.backend.model.ProductDto;
import org.example.backend.repo.ProductRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product addNewProduct(Product product) {
        return productRepo.save(product);
    }

    public Product updateProductById(String id, ProductDto value) {
        Product existing = productRepo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
        Product updated = new Product(existing.id(), value.name());
        return productRepo.save(updated);
    }
}
