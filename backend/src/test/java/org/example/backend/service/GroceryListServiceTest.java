package org.example.backend.service;

import org.example.backend.model.GroceryList;
import org.example.backend.model.Product;
import org.example.backend.model.ProductListItem;
import org.example.backend.model.Status;
import org.example.backend.repo.GroceryListRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class GroceryListServiceTest {

    @Mock
    private GroceryListRepo groceryListRepo = mock(GroceryListRepo.class);

    @InjectMocks
    private GroceryListService groceryListService = new GroceryListService(groceryListRepo);

    @Test
    void getAllGroceryLists_shouldReturnAllListsFromRepo() {
        //GIVEN
        Product banana = new Product("1", "Banana");
        Product apfel = new Product("2", "Apple");
        List<GroceryList> groceryLists = List.of(
                new GroceryList("1", List.of(
                        new ProductListItem(banana, 5)
                ), Status.OPEN),
                new GroceryList("2", List.of(
                        new ProductListItem(banana, 2),
                        new ProductListItem(apfel, 1)
                ), Status.DONE)
        );

        when(groceryListRepo.findAll()).thenReturn(groceryLists);
        //WHEN
        List<GroceryList> actualLists = groceryListService.getAllGroceryLists();
        //THEN
        verify(groceryListRepo).findAll();
        assertEquals(groceryLists, actualLists);
    }

    @Test
    void getAllGroceryLists_shouldReturnEmptyList_whenNoListsExist() {
        //GIVEN
        when(groceryListRepo.findAll()).thenReturn(Collections.emptyList());

        //WHEN
        List<GroceryList> result = groceryListService.getAllGroceryLists();

        //THEN
        verify(groceryListRepo).findAll();
        assertTrue(result.isEmpty());
    }

    @DisplayName("getAllGroceryLists — should propagate repo exception")
    @Test
    void getAllGroceryLists_shouldPropagateRepoException() {
        when(groceryListRepo.findAll()).thenThrow(new RuntimeException("DB down"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> groceryListService.getAllGroceryLists());

        assertEquals("DB down", ex.getMessage());
        verify(groceryListRepo).findAll();
    }

    @Test
    void deleteGroceryList_whenNoIdFound() {
        //GIVEN
        String id = "10";
        when(groceryListRepo.existsById(id)).thenReturn(false);

        //WHEN //THEN
        verify(groceryListRepo, never()).delete(any());
        assertThrows(NoSuchElementException.class,
                () -> groceryListService.deleteGroceryList(id));
    }

    @Test
    void deleteGroceryList_shouldReturn204_whenFound() {
        //GIVEN
        String id = "1";
        Product banana = new Product("1", "Banana");
        Product apfel = new Product("2", "Apple");
        GroceryList groceryList =
                new GroceryList("1", List.of(
                        new ProductListItem(banana, 5),
                        new ProductListItem(apfel, 3)
                ), Status.OPEN);

        when(groceryListRepo.findById(id)).thenReturn(Optional.of(groceryList));

        //WHEN
        groceryListService.deleteGroceryList(id);

        //THEN
        verify(groceryListRepo).delete(groceryList);
    }
}