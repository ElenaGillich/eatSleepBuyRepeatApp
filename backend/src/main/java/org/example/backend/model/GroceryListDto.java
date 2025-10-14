package org.example.backend.model;

import java.util.List;

public record GroceryListDto (String title, List<ProductListItem> products, Status status) {
}
