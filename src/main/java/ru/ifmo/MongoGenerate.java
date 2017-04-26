package ru.ifmo;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.clients.MongoCli;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by anastasia on 25.04.17.
 */

@Component
public class MongoGenerate {
    static List<String> names = new ArrayList<>();
    static List<String> namesMan = new ArrayList<>();
    static List<String> namesWoman = new ArrayList<>();
    static List<String> surnamesMan = new ArrayList<>();
    static List<String> surnamesWoman = new ArrayList<>();
    static List<String> patrMan = new ArrayList<>();
    static List<String> patrWoman = new ArrayList<>();
    static List<String> text = new ArrayList<>();



    @Autowired
    MongoCli mongoCli;

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/companies"))) {
            reader.lines().forEach(names::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/man"))) {
            reader.lines().forEach(namesMan::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/woman"))) {
            reader.lines().forEach(namesWoman::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/surnameMan"))) {
            reader.lines().forEach(surnamesMan::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/surnameWoman"))) {
            reader.lines().forEach(surnamesWoman::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/patrMan"))) {
            reader.lines().forEach(patrMan::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/patrWoman"))) {
            reader.lines().forEach(patrWoman::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/text"))) {
            reader.lines().forEach(text::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void generate() {
        Random rnd = new Random();
        for (int i = 0; i < 100_000; i++) {
            Document document = new Document();
            document.append("name", getName());
            document.append("numberOfBuildings", rnd.nextInt(25)+1);
            document.append("hospital", rnd.nextBoolean());
            document.append("school", rnd.nextBoolean());
            document.append("kindergarten", rnd.nextBoolean());
            document.append("subway", rnd.nextBoolean());
            mongoCli.getClient()
                    .getDatabase("company")
                    .getCollection("ApartmentComplex")
                    .insertOne(document);
        }
        for (int i = 0; i < 300_000; i++) {
            Document document = new Document();
            document.append("name", getNameMan());
            document.append("surname", getSurnameMan());
            document.append("patronymic", getPMan());
            document.append("mobile", rnd.nextInt(900000000));
            document.append("passport_data", text.get(rnd.nextInt(4604)));
            mongoCli.getClient()
                    .getDatabase("company")
                    .getCollection("Owner")
                    .insertOne(document);
        }
        for (int i = 0; i < 300_000; i++) {
            Document document = new Document();
            document.append("name", getNameWoman());
            document.append("surname", getSurnameWoman());
            document.append("patronymic", getPWoman());
            document.append("mobile", rnd.nextInt(900000000));
            document.append("passport_data", text.get(rnd.nextInt(4604)));
            mongoCli.getClient()
                    .getDatabase("company")
                    .getCollection("Owner")
                    .insertOne(document);
        }

    }

    private String getName() {
        Random rnd = new Random();
        return names.get(rnd.nextInt(69));
    }

    private String getNameMan() {
        Random rnd = new Random();
        return namesMan.get(rnd.nextInt(291));
    }

    private String getNameWoman() {
        Random rnd = new Random();
        return namesWoman.get(rnd.nextInt(217));
    }

    private String getSurnameMan() {
        Random rnd = new Random();
        return surnamesMan.get(rnd.nextInt(250));
    }

    private String getSurnameWoman() {
        Random rnd = new Random();
        return surnamesWoman.get(rnd.nextInt(250));
    }

    private String getPMan() {
        Random rnd = new Random();
        return patrMan.get(rnd.nextInt(78));
    }

    private String getPWoman() {
        Random rnd = new Random();
        return patrWoman.get(rnd.nextInt(81));
    }
}
