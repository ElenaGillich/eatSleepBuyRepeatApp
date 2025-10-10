package org.example.backend.controller;

import org.example.backend.model.Product;
import org.example.backend.model.ProductDto;
import org.example.backend.service.ProductService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Product addNewProduct(@RequestBody ProductDto product){
        return productService.addNewProduct(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id){
        if (productService.getProductById(id) != null) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
  
    @PutMapping("/{id}")
    public Product updateProductById(@PathVariable String id, @RequestBody ProductDto value) {
        return productService.updateProductById(id, value);
    }
}
