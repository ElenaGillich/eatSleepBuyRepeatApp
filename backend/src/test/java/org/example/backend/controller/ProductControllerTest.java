package org.example.backend.controller;

import org.example.backend.model.Product;
import org.example.backend.repo.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepo productRepo;

    @DirtiesContext
    @Test
    void getAllProducts_returnListWithOneProduct_WhenCalled() throws Exception {
        //given
        Product product = new Product("1", "productTest");
        productRepo.save(product);
        //when
        mockMvc.perform(get("/api/products"))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """     
                        [
                        {
                        "id": "1",
                        "name": "productTest"}
                        ]
                        """
        ));
    }

    @DirtiesContext
    @Test
    void getAllProducts_returnEmptyList_WhenNoProductAdded() throws Exception {
        //given

        //when
        mockMvc.perform(get("/api/products"))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """     
                        [
                        ]
                        """
                ));
    }

    @DirtiesContext
    @Test
    void getProductById_returnsOneProduct_whenGivenValidId() throws Exception {
        //given
        Product product = new Product("123", "Banana");
        productRepo.save(product);
        //when
        mockMvc.perform(get("/api/products/123"))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """     
                        {
                        "id": "123",
                        "name": "Banana"
                        }
                        """
                ));
    }

    @DirtiesContext
    @Test
    void getProductById_returnsNotFoundHeader_whenGivenInvalidId() throws Exception {
        //given
        Product product = new Product("123", "Banana");
        productRepo.save(product);
        //when
        mockMvc.perform(get("/api/products/123sadjlsdahlasdhgklhsdlfhasdfhljasdhf"))
                //then
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DirtiesContext
    @Test
    void deleteProduct_returnsOKHeader_whenGivenValidProduct() throws Exception {
        //given
        Product product = new Product("123", "Banana");
        productRepo.save(product);
        //when
        mockMvc.perform(
                delete("/api/products"))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DirtiesContext
    @Test
    void deleteProduct_returnsNotFoundHeader_whenGivenInvalidProduct() throws Exception {
        //given
        Product product = new Product("123", "Banana");
        productRepo.save(product);
        //when
        mockMvc.perform(
                delete("/api/products")
                .content("{\"id\":\"123456789\",\"name\":\"Banana\"}"))
                //then
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}