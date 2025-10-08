package org.example.backend.controller;

import org.example.backend.model.GroceryList;
import org.example.backend.service.GroceryListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grocery-list")
public class GroceryListController {

    private final GroceryListService service;

    public GroceryListController(GroceryListService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GroceryList>> getAllGroceryLists() {
        List<GroceryList> groceryLists = service.getAllGroceryLists();
        if(groceryLists.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(groceryLists);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroceryList(@PathVariable String id) {
        service.deleteGroceryList(id);
        return ResponseEntity.noContent().build(); //204
    }
/*
    @PostMapping
    public GroceryList addList(@RequestBody GroceryList list) {
        return service.addList(list);
    }*/
}
