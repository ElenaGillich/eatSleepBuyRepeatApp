package org.example.backend.controller;

import org.example.backend.model.*;
import org.example.backend.repo.GroceryListRepo;
import org.example.backend.repo.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    @WithMockUser
    void getAllGrocery_whenEmpty() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/grocery-list"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @DirtiesContext
    @WithMockUser
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
    @WithMockUser
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

    @DirtiesContext
    @Test
    @WithMockUser
    void getGroceryListById_shouldReturn_CorrectList() throws Exception {
        Product banana = new Product("001", "Banana");
        Product apple = new Product("002", "Apple");
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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/grocery-list/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("""
                                                                            {
                                                                              "id": "1",
                                                                              "products":
                                                                                  [
                                                                                      {
                                                                                        "product": {"id": "001", "name": "Banana"},
                                                                                        "quantity": 5
                                                                                      }
                                                                                  ],
                                                                                  "status": "OPEN"
                                                                            }
                                                                            """));
    }

    @DirtiesContext
    @Test
    @WithMockUser
    void getGroceryListById_whenNotFound_shouldThrowException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/grocery-list/4"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteGroceryList_wehenInvalid_thenStatus404() throws Exception {
        //GIVEN
        //WHEN //THEN

        mockMvc.perform(delete("/api/grocery-list/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("List with id: 1 not found!"));

    }
    @Test
    @DirtiesContext
    @WithMockUser
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

    @Test
    @WithMockUser
    void addGroceryList_shouldReturnNewCreatedGroceryList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/grocery-list")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "id": "new-123",
                      "products": [
                          {
                            "product": {
                              "id": "3",
                              "name": "banana"
                            },
                            "quantity": 2
                          },
                          {
                            "product": {
                              "id": "5",
                              "name": "apples"
                            },
                            "quantity": 10
                          }
                      ],
                      "status": "OPEN"
                    }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.status").value("OPEN"))
        .andExpect(jsonPath("$.products[0].product.name").value("banana"))
        .andExpect(jsonPath("$.products[1].quantity").value(10));
    }
}