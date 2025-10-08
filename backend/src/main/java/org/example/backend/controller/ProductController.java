package org.example.backend.controller;

import org.example.backend.model.Product;
import org.example.backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product addNewProduct(@RequestBody Product product){
        return productService.addNewProduct(product);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestBody Product product){
        if (productService.getProductById(product.id()).equals(product)) {
            productService.deleteProduct(product);
            return ResponseEntity.ok("Product deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
