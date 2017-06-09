package ru.ifmo;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.ifmo.model.graph.Building;
import ru.ifmo.model.graph.Group;
import ru.ifmo.model.graph.Worker;
import ru.ifmo.repository.graph.BuildingGraphRep;
import ru.ifmo.repository.graph.GroupGraphRep;
import ru.ifmo.repository.graph.WorkerGraphRep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by anastasia on 15.05.17.
 */
@Component
public class Neo4jGenerator {
    static List<String> namesMan = new ArrayList<>();
    static List<String> surnamesMan = new ArrayList<>();
    static List<String> text = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/man"))) {
            reader.lines().forEach(namesMan::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/surnameMan"))) {
            reader.lines().forEach(surnamesMan::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("/home/anastasia/text"))) {
            reader.lines().forEach(text::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void generate(ApplicationContext ctx) {
        BuildingGraphRep buildingGraphRep = ctx.getBean(BuildingGraphRep.class);
        GroupGraphRep groupGraphRep = ctx.getBean(GroupGraphRep.class);
        WorkerGraphRep workerGraphRep = ctx.getBean(WorkerGraphRep.class);
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Building building = new Building();
            building.setAddress(text.get(random.nextInt(4604)));
            building.setHeight(random.nextLong());
            building.setSold(random.nextBoolean());
            buildings.add(buildingGraphRep.save(building));
        }
        for (int i = 0; i < 1000; i++) {
            Group group = new Group();
            group.setComplete(random.nextInt(99)+1);
            group.setTypeOfWork(text.get(random.nextInt(4604)));
            group.setBuilding(buildings.get(random.nextInt(buildings.size())));
            groups.add(groupGraphRep.save(group));
        }
        for (int i = 0; i < 10_000; i++) {
            Worker worker = new Worker();
            worker.setGroup(groups.get(random.nextInt(groups.size())));
            worker.setName(getNameMan());
            worker.setSurname(getSurnameMan());
            worker.setOld(random.nextInt(62)+18);
            worker.setPassportData(text.get(random.nextInt(4604))+text.get(random.nextInt(4604)));
            worker.setRegistration(random.nextBoolean());
            worker.setTeamLeader(random.nextBoolean());
            worker.setSalary(random.nextInt(200000)+15000);
            workerGraphRep.save(worker);
        }
    }


    private String getNameMan() {
        Random rnd = new Random();
        return namesMan.get(rnd.nextInt(291));
    }

    private String getSurnameMan() {
        Random rnd = new Random();
        return surnamesMan.get(rnd.nextInt(250));
    }
}
