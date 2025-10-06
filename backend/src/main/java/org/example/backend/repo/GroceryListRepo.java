package org.example.backend.repo;

import org.example.backend.model.GroceryList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryListRepo extends MongoRepository<GroceryList, String> {

}
