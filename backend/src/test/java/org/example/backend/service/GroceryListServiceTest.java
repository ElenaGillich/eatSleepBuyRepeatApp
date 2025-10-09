package org.example.backend.service;

import org.example.backend.model.*;
import org.example.backend.repo.GroceryListRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class GroceryListServiceTest {

    @Mock
    private GroceryListRepo groceryListRepo = mock(GroceryListRepo.class);

    @Mock
    private IdService idService = mock(IdService.class);

    @InjectMocks
    private GroceryListService groceryListService = new GroceryListService(groceryListRepo, idService);

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
    void addGroceryList_shouldAddGroceryListToRepo() {
        //GIVEN
        GroceryListDto dto = new GroceryListDto(
            List.of(
                    new ProductListItem(new Product("1", "Milk"), 2),
                    new ProductListItem(new Product("2", "Butter"), 1)
            ), Status.OPEN
        );
        GroceryList expected = new GroceryList(
                "111",
                dto.products(),
                dto.status()
        );
        when(idService.randomId()).thenReturn("111");
        when(groceryListRepo.save(expected)).thenReturn(expected);

        //WHEN
        GroceryList actual = groceryListService.addGroceryList(dto);

        //THEN
        verify(groceryListRepo).save(expected);
        assertEquals(actual, expected);
    }
}