package org.example.backend.repo;

import org.example.backend.model.Grocery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepo extends MongoRepository<Grocery, String> {


}
