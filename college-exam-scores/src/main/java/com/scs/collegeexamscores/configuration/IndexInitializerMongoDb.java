package com.scs.collegeexamscores.configuration;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class IndexInitializerMongoDb implements CommandLineRunner {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) {
        createIndexes();
    }

    @PostConstruct
    public void createIndexes() {
        createCompoundIndex("score", "subject", "year");
        createCompoundIndex("score", "year", "score");
    }

    private void createCompoundIndex(String collection, String... fields) {
        HashMap<String, Object> indexFields = new HashMap<>();
        for (String field : fields) {
            indexFields.put(field, 1);
        }

        IndexOperations indexOps = mongoTemplate.indexOps(collection);
        if (!indexOps.getIndexInfo().stream()
                .anyMatch(index -> index.getName().equals(getIndexName(fields)))) {
            indexOps.ensureIndex(new CompoundIndexDefinition(new org.bson.Document(indexFields)));
        }
    }

    private String getIndexName(String... fields) {
        StringBuilder indexName = new StringBuilder();
        for (String field : fields) {
            indexName.append(field).append("_");
        }
        return indexName.toString();
    }
}
