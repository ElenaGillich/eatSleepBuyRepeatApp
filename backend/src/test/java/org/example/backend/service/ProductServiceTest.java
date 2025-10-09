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


    @Test
    void getAllProducts_returnListOfProducts_WhenCalled() {
        //GIVEN
        ProductRepo mockRepo = mock(ProductRepo.class);
        IdService mockIdService = mock(IdService.class);
        ProductService service = new ProductService(mockIdService, mockRepo);
        Product product1 = new Product("1", "productTest1");
        Product product2 = new Product("2", "productTest2");
        List<Product> products = List.of(product1, product2);

        //WHEN
        when(mockRepo.findAll()).thenReturn(products);
        List<Product> actual = service.getAllProducts();

        //THEN
        verify(mockRepo).findAll();
        assertEquals(products, actual);
    }

    @Test
    void updateProductById_shouldUpdateName() {
        ProductRepo mockRepo = mock(ProductRepo.class);
        IdService idService = mock(IdService.class);
        ProductService productService = new ProductService(idService, mockRepo);

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
        IdService idService = mock(IdService.class);
        ProductService productService = new ProductService(idService, mockRepo);
        ProductDto updated = new ProductDto("Pink Lady Apple");

        when(mockRepo.findById("005")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productService.updateProductById("005", updated));
        verify(mockRepo).findById("005");
        verifyNoMoreInteractions(mockRepo);
    }
    @Test
    void addNewProduct_shouldReturnListWithProduct_WhenNewProductAdded() {
        //GIVEN
        ProductRepo mockRepo = mock(ProductRepo.class);
        IdService mockIdService = mock(IdService.class);
        ProductService service = new ProductService(mockIdService, mockRepo);

        Product product = new Product("Test-Id", "productTest1");
        ProductDto newProduct = new ProductDto( "productTest1");

        //WHEN
        when(mockIdService.generateNewUuid()).thenReturn("Test-Id");
        when(mockRepo.save(product)).thenReturn(product);
        Product actual = service.addNewProduct(newProduct);

        //THEN
        verify(mockRepo).save(product);
        verify(mockIdService).generateNewUuid();
        assertEquals(product, actual);
    }

}