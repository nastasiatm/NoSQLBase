package ru.ifmo.CRUD;

/**
 * Created by anastasia on 24.05.17.
 */

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;
import org.springframework.context.ApplicationContext;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;
import ru.ifmo.model.coloumn.Apartment;
import ru.ifmo.model.coloumn.Floor;
import ru.ifmo.repository.graph.WorkerGraphRep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
public class CRUDcass {

    Scanner in = new Scanner(System.in);

    static List<String> text = new ArrayList<>();

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/text"))) {
            reader.lines().forEach(text::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cassLoop(ApplicationContext ctx) {
        try {
            while (true) {
                printMenu();
                String command = in.next();
                switch (command) {
                    case "1":
                        if (addFloor(ctx)) {
                            System.out.println("apartment was actually added");
                        } else {
                            System.out.println("Something wrong");
                        }
                        break;
                    case "2":
                        if (getApartments(ctx)) {
                            System.out.println("All apartments");
                        } else {
                            System.out.println("Something wrong");
                        }
                        break;
                    case "3":
                        if (getApartmentByID(ctx)) {
                            System.out.println("One apartment");
                        } else {
                            System.out.println("Something wrong");
                        }
                        break;
                    case "4":
                        if (updateApartment(ctx)) {
                            System.out.println("Update One apartment");
                        } else {
                            System.out.println("Something wrong");
                        }
                        break;
                    case "5":
                        if (deleteFloor(ctx)) {
                            System.out.println("Delete One apartment");
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
        } catch (Exception ex) {
            System.out.print(ex.toString());
        }
    }

    private boolean addFloor(ApplicationContext ctx){
        try{
            CassandraOperations operations = ctx.getBean(CassandraOperations.class);
            Random random = new Random();
            Apartment apartment = new Apartment();
            apartment.setFloor(random.nextInt(100)+1);
            apartment.setId(UUID.randomUUID().toString());
            apartment.setId_mongo(text.get(random.nextInt(4604)));
            System.out.print("Input price of apartment");
            Integer price = in.nextInt();
            apartment.setPrice(price);
            System.out.print("Input room number of apartment");
            Integer room = in.nextInt();
            apartment.setRooms(room);
            operations.insert(apartment);
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean getApartmentByID(ApplicationContext ctx){
        try{
            CassandraOperations operations = ctx.getBean(CassandraOperations.class);
            Apartment ap = new Apartment();
            System.out.print("Input id of apartment");
            String id = in.next();
            System.out.print(operations.selectOne("select * from apartment where id='" + id + "'", Apartment.class).toString());
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean getApartments(ApplicationContext ctx){
        try{
            CassandraOperations operations = ctx.getBean(CassandraOperations.class);
            System.out.print(operations.selectOne("select * from apartment", Apartment.class).toString());
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean updateApartment(ApplicationContext ctx){
        try{
            CassandraOperations operations = ctx.getBean(CassandraOperations.class);
            Apartment ap;
            System.out.print("Input id of apartment");
            String id = in.next();
            ap = operations.selectOne("select * from apartment where id='" + id + "'", Apartment.class);
            System.out.print("Input price of apartment");
            Integer price = in.nextInt();
            ap.setPrice(price);
            System.out.print("Input rooms of apartment");
            Integer rooms = in.nextInt();
            ap.setRooms(rooms);
            System.out.print("Input floor of apartment");
            Integer floor = in.nextInt();
            ap.setRooms(floor);
            System.out.print("Input is apartment sold");
            Boolean sold = in.nextBoolean();
            ap.setSold(sold);
            operations.update(ap);
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean deleteFloor(ApplicationContext ctx){
        try{
            CassandraOperations operations = ctx.getBean(CassandraOperations.class);
            Apartment ap;
            System.out.print("Input id of apartment");
            String id = in.next();
            ap = operations.selectOne("select * from apartment where id='" + id + "'", Apartment.class);
            operations.delete(ap);
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }


    private void printMenu() {
        StringBuilder builder = new StringBuilder("\tSelect command\n");
        builder.append("1. Add apartment\n");
        builder.append("2. Get all apartments\n");
        builder.append("3. Get apartment by id\n");
        builder.append("4. Update apartment\n");
        builder.append("5. Delete apartment by id\n");
        builder.append("q. Quit");
        System.out.println(builder.toString());
    }
}
