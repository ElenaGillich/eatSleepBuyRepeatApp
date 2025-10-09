package org.example.backend.model;

import java.util.List;

public record GroceryListDto (List<ProductListItem> products, Status status) {
}
