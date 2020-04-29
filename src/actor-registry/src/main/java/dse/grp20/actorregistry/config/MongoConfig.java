package dse.grp20.actorregistry.config;

import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;


public class MongoConfig {

    //@Bean
    public MongoClient mongo() {
        //set sslEnabled to true here
        MongoClientOptions options = MongoClientOptions.builder()
                .readPreference(ReadPreference.primaryPreferred())
                .retryWrites(true)
                .maxConnectionIdleTime(6000)
                .sslEnabled(true)
                .build();

        return null;


        /*MongoClient client =  MongoClients.create(options,
                "mongodb+srv://ngriebenow:dse-mongo-db@dse-actor-registry-service-9ztit.mongodb.net/test?retryWrites=true&w=majority");

        return client;*/
    }

    //@Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "test");
    }
}
