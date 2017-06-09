package ru.ifmo;

import org.springframework.context.ApplicationContext;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;
import ru.ifmo.model.coloumn.Apartment;
import ru.ifmo.model.coloumn.Floor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by anastasia on 15.05.17.
 */
@Component
public class CassandraGenerate {
    static List<String> text = new ArrayList<>();

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/text"))) {
            reader.lines().forEach(text::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void generate(ApplicationContext ctx) {
        CassandraOperations operations = ctx.getBean(CassandraOperations.class);
        Random random = new Random();
        for (int i = 0; i < 1_000_000; i++) {
            Apartment apartment = new Apartment();
            Floor floor = new Floor();
            apartment.setFloor(random.nextInt(100)+1);
            apartment.setId(UUID.randomUUID().toString());
            apartment.setId_mongo(text.get(random.nextInt(4604)));
            apartment.setPrice(random.nextInt(10000000));
            apartment.setRooms(random.nextInt(5)+1);
            apartment.setSold(random.nextBoolean());
            apartment.setNumber(random.nextInt(1000)+1);
            apartment.setSquare(random.nextInt(500)+20);
            floor.setId(UUID.randomUUID().toString());
            floor.setIdBuildFrom4(UUID.randomUUID().toString());
            floor.setNumber(random.nextInt(25)+1);
            operations.insert(apartment);
            operations.insert(floor);
        }
    }
}
