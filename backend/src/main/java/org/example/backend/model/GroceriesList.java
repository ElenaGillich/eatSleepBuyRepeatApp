package org.example.backend.model;

import java.util.Map;

public record GroceriesList(String id, Map<Grocery, Integer> groceries, Status status) {
}
