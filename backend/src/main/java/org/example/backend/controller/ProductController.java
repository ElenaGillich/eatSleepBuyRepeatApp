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
        ResponseEntity<Product> response;
        Product product = productService.getProductById(id);
        if (product != null) {
            response = new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping
    public Product addNewProduct(@RequestBody ProductDto product){
        return productService.addNewProduct(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id){
        ResponseEntity<String> response;
        if (productService.getProductById(id) != null) {
            productService.deleteProduct(id);
            response = ResponseEntity.ok("Product deleted");
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }
  
    @PutMapping("/{id}")
    public Product updateProductById(@PathVariable String id, @RequestBody ProductDto value) {
        return productService.updateProductById(id, value);
    }
}
