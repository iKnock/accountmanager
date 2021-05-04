package it.fabrick.api.accountmanager.repository;

import it.fabrick.api.accountmanager.models.input.ApiInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author HSelato
 */
public interface ApiInfoRepository extends MongoRepository<ApiInformation, String> {

    @Query("{operationName:'?0'}")
    ApiInformation findByOperationName(String operationName);
}
