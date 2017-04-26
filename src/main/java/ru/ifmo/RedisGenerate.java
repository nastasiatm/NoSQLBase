package ru.ifmo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.clients.RedisCli;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by anastasia on 25.04.17.
 */
@Component
public class RedisGenerate {
    static List<String> namesOfMaterials = new ArrayList<>();
    static List<String> namesOfEquipments = new ArrayList<>();

    @Autowired
    RedisCli redisClient;

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/materials"))) {
            reader.lines().forEach(namesOfMaterials::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try (BufferedReader reader2 = new BufferedReader(new FileReader("/home/anastasia/equipments"))) {
            reader2.lines().forEach(namesOfEquipments::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generate() {
        Random rnd = new Random();
        //material name
        System.out.println("Generating materials...");
        Map<String, String> material = new HashMap<>();
        for (int i = 0; i < 250_000; i++) {
            material.put(getNameOfMaterials(), "" + rnd.nextInt(1000000)+1);
        }
        redisClient.getJedis().hmset("material", material);

        //equipment name
        System.out.println("Generating equipments...");
        Map<String, String> equipment = new HashMap<>();
        for (int i = 0; i < 250_000; i++) {
            equipment.put(getNameOfEquipment(), "" + rnd.nextInt(1000000)+1);
        }
        redisClient.getJedis().hmset("material", material);
    }


    private String getNameOfMaterials() {
        Random rnd = new Random();
        return namesOfMaterials.get(rnd.nextInt(149));
    }

    private String getNameOfEquipment() {
        Random rnd = new Random();
        return namesOfEquipments.get(rnd.nextInt(1));
    }
}
