package org.example.backend.service;

import org.example.backend.model.GroceryList;
import org.example.backend.repo.GroceryListRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GroceryListService {

    private final GroceryListRepo groceryListRepo;

    public GroceryListService(GroceryListRepo groceryListRepo) {
        this.groceryListRepo = groceryListRepo;
    }

    public List<GroceryList> getAllGroceryLists() {
        return groceryListRepo.findAll();
    }

    public void deleteGroceryList(String id) {
        GroceryList list = groceryListRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("List with id: " + id + " not found!"));
        groceryListRepo.delete(list);
    }
/*
    public GroceryList addList(GroceryList list) {
        return groceryListRepo.save(list);
    }*/
}
