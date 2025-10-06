package org.example.backend.controller;

import org.example.backend.model.GroceryList;
import org.example.backend.service.GroceryListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/grocery-list")
public class GroceryListController {

    private final GroceryListService service;

    public GroceryListController(GroceryListService service) {
        this.service = service;
    }

    @GetMapping
    public List<GroceryList> getAllGroceryLists() {
        return service.getAllGroceryLists();
}

}
