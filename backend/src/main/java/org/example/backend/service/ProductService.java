package org.example.backend.service;

import org.example.backend.model.Product;
import org.example.backend.repo.ProductRepo;
import org.springframework.stereotype.Service;

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

    public Product getProductById(String id) { return productRepo.getById(id); }

    public Product addNewProduct(Product product) { return productRepo.save(product); }

    public void deleteProduct(String id) { productRepo.deleteById(id); }
}
