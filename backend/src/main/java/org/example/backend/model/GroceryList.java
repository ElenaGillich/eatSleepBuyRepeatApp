package org.example.backend.model;

import lombok.With;

import java.util.List;

@With
public record GroceryList(String id, List<ProductListItem> products, Status status) {

}
