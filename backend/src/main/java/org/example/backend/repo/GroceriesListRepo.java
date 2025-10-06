package org.example.backend.repo;

import org.example.backend.model.GroceriesList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceriesListRepo extends MongoRepository<GroceriesList, String> {

}
