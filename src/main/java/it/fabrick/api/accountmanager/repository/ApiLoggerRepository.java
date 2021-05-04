package it.fabrick.api.accountmanager.repository;

import it.fabrick.api.accountmanager.models.input.ApiLogger;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author HSelato
 */
public interface ApiLoggerRepository extends MongoRepository<ApiLogger, String> {
    
}
