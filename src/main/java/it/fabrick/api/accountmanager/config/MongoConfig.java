package it.fabrick.api.accountmanager.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author HSelato
 */
@Configuration
@EnableMongoRepositories(basePackages = "it.fabrick.api.accountmanager.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return env.getProperty("spring.data.mongodb.database");
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString conString = new ConnectionString(env.getProperty("spring.data.mongodb.uri"));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(conString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

}
