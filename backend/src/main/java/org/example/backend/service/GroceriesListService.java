package org.example.backend.service;

import org.example.backend.model.GroceriesList;
import org.example.backend.model.Grocery;
import org.example.backend.repo.GroceriesListRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceriesListService {

    private final GroceriesListRepo groceriesListRepo;

    public GroceriesListService(GroceriesListRepo groceriesListRepo) {
        this.groceriesListRepo = groceriesListRepo;
    }

    public List<GroceriesList> getAllGroceryLists() {

        return null;
    }
}
