package org.example.backend.controller;

import org.example.backend.model.GroceryList;
import org.example.backend.model.Product;
import org.example.backend.model.ProductListItem;
import org.example.backend.model.Status;
import org.example.backend.repo.GroceryListRepo;
import org.example.backend.repo.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GroceryListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    GroceryListRepo groceryListRepo;

    @BeforeEach
    void setUp() {
        productRepo.deleteAll();
        groceryListRepo.deleteAll();
    }

    @Test
    @DirtiesContext
    void getAllGrocery_whenEmpty() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/grocery-list"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @DirtiesContext
    void getAllGrocery_whenNotEmpty_return200AndExpectedJson() throws Exception {
        //GIVEN
        Product banana = new Product("1", "banana");
        Product apple = new Product("2", "apple");
        productRepo.saveAll(List.of(banana, apple));

        GroceryList list1 = new GroceryList(
                "1",
                List.of(new ProductListItem(banana, 5)),
                Status.OPEN
        );

        GroceryList list2 = new GroceryList(
                "2",
                List.of(new ProductListItem(banana, 2),
                        new ProductListItem(apple, 1)),
                Status.DONE
        );
        groceryListRepo.saveAll(List.of(list1, list2));

        //WHEN //THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/grocery-list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                                                      [
                                                        {
                                                          "id": "1",
                                                          "products": [
                                                            {
                                                              "product": { "id": "1", "name": "banana" },
                                                              "quantity": 5
                                                            }
                                                          ],
                                                          "status": "OPEN"
                                                        },
                                                        {
                                                          "id": "2",
                                                          "products": [
                                                            {
                                                              "product": { "id": "1", "name": "banana" },
                                                              "quantity": 2
                                                            },
                                                            {
                                                              "product": { "id": "2", "name": "apple" },
                                                              "quantity": 1
                                                            }
                                                          ],
                                                          "status": "DONE"
                                                        }
                                                      ]
                                                    """));

    }

    @Test
    @DirtiesContext
    void getAll_structureSmokeTest() throws Exception {
        productRepo.save(new Product("1", "Milk"));
        groceryListRepo.save(new GroceryList("1",
                List.of(new ProductListItem(new Product("1","Milk"), 2)), Status.OPEN));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/grocery-list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].status").value("OPEN"))
                .andExpect(jsonPath("$[0].products[0].product.id").value("1"))
                .andExpect(jsonPath("$[0].products[0].quantity").value(2));
    }

    @Test
    @DirtiesContext
    void deleteGroceryList_wehenInvalid_thenStatus404() throws Exception {
        //GIVEN
        //WHEN //THEN

        mockMvc.perform(delete("/api/grocery-list/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("List with id: 1 not found!"));

    }
    @Test
    @DirtiesContext
    void deleteGroceryList_shouldReturnStatus204() throws Exception {
        //GIVEN
        Product banana = new Product("1", "banana");
        Product apple = new Product("2", "apple");
        productRepo.saveAll(List.of(banana, apple));

        GroceryList list1 = new GroceryList(
                "1",
                List.of(new ProductListItem(banana, 5)),
                Status.OPEN
        );
        groceryListRepo.save(list1);

        //WHEN //THEN
        mockMvc.perform(delete("/api/grocery-list/1"))
                .andExpect(status().isNoContent());

    }
}