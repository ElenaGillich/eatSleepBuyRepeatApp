package org.example.backend.controller;

import org.example.backend.model.GroceriesList;
import org.example.backend.model.Grocery;
import org.example.backend.service.GroceriesListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groceries")
public class GroceriesListController {

    private final GroceriesListService service;

    public GroceriesListController(GroceriesListService service) {
        this.service = service;
    }

    @GetMapping
    public List<GroceriesList> getAllGroceryLists() {
        return service.getAllGroceryLists();
}

}
