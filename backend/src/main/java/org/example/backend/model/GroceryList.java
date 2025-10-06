package org.example.backend.model;

import java.util.Map;

public record GroceryList(String id, Map<Product, Integer> products, Status status) {
}
