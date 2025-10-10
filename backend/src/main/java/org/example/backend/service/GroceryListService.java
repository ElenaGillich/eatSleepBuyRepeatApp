package org.example.backend.service;

import org.example.backend.model.GroceryList;
import org.example.backend.model.GroceryListDto;
import org.example.backend.repo.GroceryListRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GroceryListService {

    private final GroceryListRepo groceryListRepo;
    private final IdService idService;

    public GroceryListService(GroceryListRepo groceryListRepo, IdService idService) {
        this.groceryListRepo = groceryListRepo;
        this.idService = idService;
    }

    public List<GroceryList> getAllGroceryLists() {
        return groceryListRepo.findAll();
    }

    public GroceryList getGroceryListById(String id) {
        return groceryListRepo.findById(id).
                orElseThrow(() -> new NoSuchElementException("List with id: " + id + " not found."));
    }

    public void deleteGroceryList(String id) {
        GroceryList list = groceryListRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("List with id: " + id + " not found!"));
        groceryListRepo.delete(list);
    }

    public GroceryList addGroceryList(GroceryListDto groceryListDto) {
        GroceryList newGroceryList = new GroceryList(
                idService.randomId(),
                groceryListDto.products(),
                groceryListDto.status()
        );

        return groceryListRepo.save(newGroceryList);
    }
}
