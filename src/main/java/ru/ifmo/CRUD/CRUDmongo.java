package ru.ifmo.CRUD;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.clients.MongoCli;
import com.mongodb.client.model.Filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by anastasia on 17.05.17.
 */


@Component
public class CRUDmongo {
    @Autowired
    MongoCli mongoCli = new MongoCli("localhost", 27017);

    Scanner in = new Scanner(System.in);

    public void mongoLoop() {
        try {
            while (true) {
                printMenu();
                String command = in.next();
                switch (command) {
                    case "1":
                        if (addComplex()) {
                            System.out.println("Complex was actually added");
                        } else {
                            System.out.println("Something wrong");
                        }
                        break;
                    case "2":
                        if (getComplex()) {
                            System.out.println("All complexes");
                        } else {
                            System.out.println("Something wrong");
                        }
                        break;
                    case "3":
                        if (getComplexByID()) {
                            System.out.println("One complex");
                        } else {
                            System.out.println("Something wrong");
                        }
                        break;
                    case "4":
                        if (updateComplex()) {
                            System.out.println("Update One complex");
                        } else {
                            System.out.println("Something wrong");
                        }
                        break;
                    case "5":
                        if (deleteComplex()) {
                            System.out.println("Delete One complex");
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
        catch (IOException ex){
            System.out.print(ex.toString());
        }
    }

    private boolean addComplex() throws IOException{
        try {
            Document document = new Document();
            document.append("complex_id", UUID.randomUUID().toString());
            System.out.println("Input complex name");
            String name = in.next();
            document.append("name", name);
            System.out.println("Input number of buildings");
            document.append("numberOfBuildings", Integer.parseInt(in.next()));
            /*document.append("hospital", Boolean.parseBoolean(in.next()));
            document.append("school", Boolean.parseBoolean(in.next()));
            document.append("kindergarten", Boolean.parseBoolean(in.next()));
            document.append("subway", Boolean.parseBoolean(in.next()));*/
            mongoCli.getClient()
                    .getDatabase("company")
                    .getCollection("ApartmentComplex")
                    .insertOne(document);
            return true;
        }
        catch (Exception e) {
            System.out.print(e.toString());
            return false;
        }
    }

    private boolean getComplex() throws IOException {

        try {
            MongoCursor<Document> cursor = mongoCli.getClient()
                    .getDatabase("company")
                    .getCollection("ApartmentComplex").find().iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toString());
            }
            cursor.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean getComplexByID() throws IOException {
        try {
            System.out.println("Input ID");
            Integer id = in.nextInt();
            System.out.println(mongoCli.getClient()
                    .getDatabase("company")
                    .getCollection("ApartmentComplex").find(eq("id", id)).toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean deleteComplex() throws IOException{
        try {
            System.out.println("Input ID");
            Integer id = in.nextInt();
            System.out.println(mongoCli.getClient()
                    .getDatabase("company")
                    .getCollection("ApartmentComplex").deleteOne(eq("id", id)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private boolean updateComplex() throws IOException{
        try {
            System.out.println("Input ID");
            Integer id = in.nextInt();
            System.out.println("Input new name of complex");
            String newName = in.nextLine();
            System.out.println(mongoCli.getClient()
                    .getDatabase("company")
                    .getCollection("ApartmentComplex").updateOne(eq("id", id),new Document("$set", new Document("newName", newName))));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void printMenu() {
        StringBuilder builder = new StringBuilder("\tSelect command\n");
        builder.append("1. Add complex\n");
        builder.append("2. Get all complex\n");
        builder.append("3. Get complex by id\n");
        builder.append("4. Update complex\n");
        builder.append("3. Delete complex by id\n");
        builder.append("q. Quit");
        System.out.println(builder.toString());
    }
}
