package org.example.backend.service;

import org.example.backend.model.*;
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
                new GroceryList("1", "Title A", List.of(
                        new ProductListItem(banana, 5)
                ), Status.OPEN),
                new GroceryList("2", "Title B", List.of(
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
    void getGroceryListById_shouldReturn_CorrectList() {
        Product kiwi = new Product("004", "Kiwi");
        Product grapefruit = new Product("005", "Grapefruit");

        GroceryList groceryList1 =
                new GroceryList("1", "Title A", List.of(
                        new ProductListItem(kiwi, 5),
                        new ProductListItem(grapefruit, 10)
                ), Status.OPEN
                );

        GroceryList groceryList2 =
                new GroceryList("2", "Title B", List.of(
                        new ProductListItem(kiwi, 7),
                        new ProductListItem(grapefruit, 17)
                ), Status.OPEN
                );

        when(groceryListRepo.findById("2")).thenReturn(Optional.of(groceryList2));

        groceryListService.getGroceryListById("2");

        verify(groceryListRepo).findById("2");
        verifyNoMoreInteractions(groceryListRepo);
    }

    @Test
    void getGroceryListById_whenIdNotFound_shouldThrowException() {
        when(groceryListRepo.findById("5")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> groceryListService.getGroceryListById("5"));
        verify(groceryListRepo).findById("5");
        verifyNoMoreInteractions(groceryListRepo);
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
                new GroceryList("1", "Title A", List.of(
                        new ProductListItem(banana, 5),
                        new ProductListItem(apfel, 3)
                ), Status.OPEN);

        when(groceryListRepo.findById(id)).thenReturn(Optional.of(groceryList));

        //WHEN
        groceryListService.deleteGroceryList(id);

        //THEN
        verify(groceryListRepo).delete(groceryList);
    }
    @Test
    void addGroceryList_shouldAddGroceryListToRepo() {
        //GIVEN
        GroceryListDto dto = new GroceryListDto("Title A",
            List.of(
                    new ProductListItem(new Product("1", "Milk"), 2),
                    new ProductListItem(new Product("2", "Butter"), 1)
            ), Status.OPEN
        );
        GroceryList expected = new GroceryList(
                "111",
                "Title A",
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