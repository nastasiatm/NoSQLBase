package ru.ifmo.clients;

import com.mongodb.MongoClient;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Created by anastasia on 19.04.17.
 */
@Component
public class MongoCli {
    @Getter
    private MongoClient client;
    private final String DB_NAME="company";

    public MongoCli(String host, int port) {
        this.client = new MongoClient("localhost", port);
    }




}
