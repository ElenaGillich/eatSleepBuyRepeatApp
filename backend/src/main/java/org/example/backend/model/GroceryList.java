package org.example.backend.model;

import java.util.List;

public record GroceryList(String id, List<ProductListItem> products, Status status) {

}
