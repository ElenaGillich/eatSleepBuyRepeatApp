package org.example.backend.model;

import java.util.List;

public record GroceryList(String id, String title, List<ProductListItem> products, Status status) {

}
