package org.example.backend.service;

import org.example.backend.model.GroceryList;
import org.example.backend.repo.GroceryListRepo;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public GroceryList addGroceryList(GroceryList groceryList) {
        GroceryList newGroceryList = groceryList.withId(idService.randomId());

        return groceryListRepo.save(newGroceryList);
    }
}
