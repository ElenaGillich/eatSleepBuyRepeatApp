package org.example.backend.service;

import org.example.backend.model.Product;
import org.example.backend.repo.ProductRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class ProductServiceTest {

    ProductRepo repo = mock(ProductRepo.class);
    ProductService service = new ProductService(repo);

    @Test
    void getAllProducts_returnListOfProducts_WhenCalled() {
        //GIVEN
        Product product1 = new Product("1", "productTest1");
        Product product2 = new Product("2", "productTest2");
        List<Product> products = List.of(product1, product2);

        //WHEN
        when(repo.findAll()).thenReturn(products);
        List<Product> actual = service.getAllProducts();

        //THEN
        verify(repo).findAll();
        assertEquals(products, actual);
    }

    @Test
    void getProductById_returnsProduct_whenCalled() {
        assertTrue(true);
    }

    @Test
    void deleteProduct() {
        assertTrue(true);
    }
}