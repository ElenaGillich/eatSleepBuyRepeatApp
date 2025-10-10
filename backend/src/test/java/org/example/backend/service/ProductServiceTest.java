package org.example.backend.service;

import org.example.backend.model.Product;
import org.example.backend.model.ProductDto;
import org.example.backend.repo.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    void getProductById_returnsProduct_whenCalledWithValidID() {
        //given
        ProductRepo mockRepo = mock(ProductRepo.class);
        ProductService productService = new ProductService(mockRepo);
        Product expected = new Product("1", "productTest1");
        when(mockRepo.findById("1")).thenReturn(Optional.of(expected));

        //when
        Product actual = productService.getProductById("1");

        //then
        assertEquals(expected, actual);
    }

    @Test
    void getProductById_throwsException_whenCalledWithInvalidID() {
        //given
        ProductRepo mockRepo = mock(ProductRepo.class);
        ProductService productService = new ProductService(mockRepo);
        String id = "kfjghlföhgldfshgödfl";
        Product expected = new Product(id, "productTest1");

        when(mockRepo.findById(id)).thenReturn(Optional.of(expected));

        //then
        assertThrows(ResponseStatusException.class, ()->productService.getProductById("111"));
    }
  
    @Test
    void updateProductById_shouldUpdateName() {
        ProductRepo mockRepo = mock(ProductRepo.class);
        ProductService productService = new ProductService(mockRepo);

        Product existing = new Product("001", "Apple");
        ProductDto updated = new ProductDto("Pink Lady Apple");

        when(mockRepo.findById("001")).thenReturn(Optional.of(existing));
        when(mockRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        Product updatedApple = productService.updateProductById("001", updated);

        assertEquals("001", updatedApple.id());
        assertEquals("Pink Lady Apple", updatedApple.name());
        verify(mockRepo).findById("001");
        verify(mockRepo).save(updatedApple);
        verifyNoMoreInteractions(mockRepo);
    }

    @Test
    void updateProductById_shouldThrowException() {
        ProductRepo mockRepo = mock(ProductRepo.class);
        ProductService productService = new ProductService(mockRepo);
        ProductDto updated = new ProductDto("Pink Lady Apple");

        when(mockRepo.findById("005")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productService.updateProductById("005", updated));
        verify(mockRepo).findById("005");
        verifyNoMoreInteractions(mockRepo);
    }

}