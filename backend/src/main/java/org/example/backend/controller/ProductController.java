package org.example.backend.controller;

import org.example.backend.model.Product;
import org.example.backend.model.ProductDto;
import org.example.backend.service.ProductService;
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
    public Product getProductById(@PathVariable String id) { return productService.getProductById(id); }

    @PostMapping
    public Product addNewProduct(@RequestBody ProductDto product){
        return productService.addNewProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) { productService.deleteProduct(id); }
  
    @PutMapping("/{id}")
    public Product updateProductById(@PathVariable String id, @RequestBody ProductDto value) {
        return productService.updateProductById(id, value);
    }
}
