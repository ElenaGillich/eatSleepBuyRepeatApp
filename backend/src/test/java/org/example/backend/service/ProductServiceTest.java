package org.example.backend.service;

import org.example.backend.exception.EmptyProductNameException;
import org.example.backend.model.Product;
import org.example.backend.model.ProductDto;
import org.example.backend.repo.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class ProductServiceTest {

    private ProductRepo mockRepo;
    private IdService mockIdService;
    private ProductService productService;

    @BeforeEach
    void setup() {
         mockRepo = mock(ProductRepo.class);
         mockIdService = mock(IdService.class);
         productService = new ProductService(mockIdService, mockRepo);
    }

    @Test
    void getAllProducts_returnListOfProducts_WhenCalled() {
        //GIVEN
        Product product1 = new Product("1", "productTest1");
        Product product2 = new Product("2", "productTest2");
        List<Product> products = List.of(product1, product2);

        //WHEN
        when(mockRepo.findAll()).thenReturn(products);
        List<Product> actual = productService.getAllProducts();

        //THEN
        verify(mockRepo).findAll();
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

        ProductDto updated = new ProductDto("Pink Lady Apple");

        when(mockRepo.findById("005")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productService.updateProductById("005", updated));
        verify(mockRepo).findById("005");
        verifyNoMoreInteractions(mockRepo);
    }

    @Test
    void addNewProduct_shouldReturnProduct_WhenNewProductAdded() {
        //GIVEN

        Product product = new Product("Test-Id", "productTest1");
        ProductDto newProduct = new ProductDto("productTest1");

        //WHEN
        when(mockIdService.randomId()).thenReturn("Test-Id");
        when(mockRepo.save(product)).thenReturn(product);
        Product actual = productService.addNewProduct(newProduct);

        //THEN
        verify(mockRepo).save(product);
        verify(mockIdService).randomId();
        assertEquals(product, actual);
    }

    @Test
    void addNewProduct_shouldThrowException_WhenEmptyStringIsAdded() {
        //GIVEN

        ProductDto newProduct = new ProductDto("");

        //THEN
        assertThrows(EmptyProductNameException.class, () -> {
            throw new EmptyProductNameException("Empty Strings are not allowed. Please enter a name.");
        });

        Throwable exception = assertThrows(EmptyProductNameException.class,
                () -> productService.addNewProduct(newProduct));
        assertEquals("Empty Strings are not allowed. Please enter a name.", exception.getMessage());
    }

    @Test
    void addNewProduct_shouldThrowException_WhenStringWithOnlySpacesIsAdded() {
        //GIVEN

        ProductDto newProduct = new ProductDto("     ");

        //THEN
        assertThrows(EmptyProductNameException.class, () -> {
            throw new EmptyProductNameException("Empty Strings are not allowed. Please enter a name.");
        });

        Throwable exception = assertThrows(EmptyProductNameException.class,
                () -> productService.addNewProduct(newProduct));
        assertEquals("Empty Strings are not allowed. Please enter a name.", exception.getMessage());
    }

}