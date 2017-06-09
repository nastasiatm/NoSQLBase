package ru.ifmo.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.clients.RedisCli;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by anastasia on 17.05.17.
 */
@Component
public class CRUDredis {
    @Autowired
    RedisCli redisClient = new RedisCli();

    Scanner in = new Scanner(System.in);

    public void redisLoop() {
        try {
            while (true) {
                printMenu();
                String command = in.next();
                switch (command) {
                    case "1":
                        if (addMaterial()) {
                            System.out.println("\nMaterial was actually added");
                        } else {
                            System.out.println("\nSomething wrong");
                        }
                        break;
                    case "2":
                        if (getMaterial()) {
                            System.out.println("\nAll materials");
                        } else {
                            System.out.println("\nSomething wrong");
                        }
                        break;
                    case "3":
                        if (getMaterialByName()) {
                            System.out.println("\nOne material");
                        } else {
                            System.out.println("\nSomething wrong");
                        }
                        break;
                    case "4":
                        if (updateMaterial()) {
                            System.out.println("\nUpdate One material");
                        } else {
                            System.out.println("\nSomething wrong");
                        }
                        break;
                    case "5":
                        if (deleteMaterial()) {
                            System.out.println("Delete One material");
                        } else {
                            System.out.println("Something wrong");
                        }
                        break;
                    case "q":
                        return;
                    default:
                        System.out.println("Not a command. Retry input");
                        break;
                }
            }
        }
        catch (Exception ex){
            System.out.print(ex.toString());
        }
    }
    private boolean addMaterial () {
        try{
            Map<String, String> material = new HashMap<>();
            System.out.println("Input material name");
            String name = in.next();
            System.out.println("Input material cost");
            Integer cost = in.nextInt();
            material.put(name, "" + cost);
            redisClient.getJedis().hmset("material", material);
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean getMaterialByName () {
        try{
            System.out.println("Input material name");
            String name = in.next();
            System.out.print(redisClient.getJedis().hmget("material", name).toString());
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean getMaterial () {
        try{
            Map<String, String> material = new HashMap<>();
            material = redisClient.getJedis().hgetAll("material");
            for (Map.Entry entry : material.entrySet()) {
                System.out.println(entry.getKey() + ", " + entry.getValue());
            }
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean updateMaterial () {
        try{
            System.out.println("Input material name");
            String name = in.next();
            System.out.println("Input new material cost");
            String cost = in.next();
            System.out.print(redisClient.getJedis().hset("material", name, cost));
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean deleteMaterial () {
        try{
            System.out.println("Input material name");
            String name = in.next();
            System.out.print(redisClient.getJedis().hdel("material", name));
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }
    private void printMenu() {
        StringBuilder builder = new StringBuilder("\tSelect command\n");
        builder.append("1. Add material\n");
        builder.append("2. Get all materials\n");
        builder.append("3. Get material by name\n");
        builder.append("4. Update material\n");
        builder.append("5. Delete material by name\n");
        builder.append("q. Quit");
        System.out.println(builder.toString());
    }
}
