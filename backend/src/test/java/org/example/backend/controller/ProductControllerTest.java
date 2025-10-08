package org.example.backend.controller;
import static org.junit.jupiter.api.Assertions.*;
import org.example.backend.model.Product;
import org.example.backend.model.ProductDto;
import org.example.backend.repo.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
    void updateProductById_shouldUpdateProduct() throws Exception {
        Product banana = new Product("001", "Banana");
        productRepo.save(banana);
        ProductDto bananaDto = new ProductDto("Chiquita Banana");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/001")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                              "name": "Chiquita Banana"
                            }
                        """)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                                                           {
                                                                             "id": "001",
                                                                             "name": "Chiquita Banana"
                                                                           }
                                                                           """));
    }

    @DirtiesContext
    @Test
    void updateProductById_shouldThrowException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/005")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                              "name": "Pink Lady Apple"
                            }
                        """)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


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

}