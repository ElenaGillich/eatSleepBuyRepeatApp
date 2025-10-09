package org.example.backend.service;

import org.example.backend.exceptions.EmptyProductNameException;
import org.example.backend.model.Product;
import org.example.backend.model.ProductDto;
import org.example.backend.repo.ProductRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {

    private final IdService idService;
    private final ProductRepo productRepo;

    public ProductService(IdService idService, ProductRepo productRepo) {
        this.idService = idService;
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product addNewProduct(ProductDto product) {
        if (product.name().isBlank()){
            throw new EmptyProductNameException("Empty Strings are not allowed. Please enter a name.");
        }
        Product newProduct = new Product(idService.generateNewUuid(), product.name());
        return productRepo.save(newProduct);
    }

    public Product updateProductById(String id, ProductDto value) {
        Product existing = productRepo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
        Product updated = new Product(existing.id(), value.name());
        return productRepo.save(updated);
    }
}
