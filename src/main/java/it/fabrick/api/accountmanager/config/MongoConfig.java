package it.fabrick.api.accountmanager.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author HSelato
 */
@Configuration
@EnableMongoRepositories(basePackages = "it.fabrick.api.accountmanager.repository")
public class MongoConfig {

    @Autowired
    private Environment env;

    protected String getDatabaseName() {
        return env.getProperty("spring.data.mongodb.database");
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(env.getProperty("spring.data.mongodb.uri"));
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(mongoClient(), getDatabaseName());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory());
        mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);        
        return mongoTemplate;
    }
}
