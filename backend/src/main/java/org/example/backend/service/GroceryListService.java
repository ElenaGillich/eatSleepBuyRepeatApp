package org.example.backend.service;

import org.example.backend.model.GroceryList;
import org.example.backend.repo.GroceryListRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryListService {

    private final GroceryListRepo groceryListRepo;

    public GroceryListService(GroceryListRepo groceryListRepo) {
        this.groceryListRepo = groceryListRepo;
    }

    public List<GroceryList> getAllGroceryLists() {

        return null;
    }
}
