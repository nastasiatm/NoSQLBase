package ru.ifmo.CRUD;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.ifmo.model.graph.Building;
import ru.ifmo.model.graph.Group;
import ru.ifmo.model.graph.Worker;
import ru.ifmo.repository.graph.WorkerGraphRep;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by anastasia on 17.05.17.
 */
@Component
public class CRUDneo {
    Scanner in = new Scanner(System.in);
    //ApplicationContext ctx;
    //WorkerGraphRep workerGraphRep = ctx.getBean(WorkerGraphRep.class);
    public void neoLoop(ApplicationContext ctx) {
        try {
            while (true) {
                printMenu();
                String command = in.next();
                switch (command) {
                    case "1":
                        if (addWorker(ctx)) {
                            System.out.println("\nWorker was actually added");
                        } else {
                            System.out.println("\nSomething wrong");
                        }
                        break;
                    case "2":
                        if (getWorkers(ctx)) {
                            System.out.println("\nAll workers");
                        } else {
                            System.out.println("\nSomething wrong");
                        }
                        break;
                    case "3":
                        if (getWorkerById(ctx)) {
                            System.out.println("\nOne worker");
                        } else {
                            System.out.println("\nSomething wrong");
                        }
                        break;
                    case "4":
                        if (updateWorker(ctx)) {
                            System.out.println("\nUpdate One worker");
                        } else {
                            System.out.println("\nSomething wrong");
                        }
                        break;
                    case "5":
                        if (deleteWorker(ctx)) {
                            System.out.println("Delete One worker");
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
    private boolean addWorker (ApplicationContext ctx) {
        try{
            WorkerGraphRep workerGraphRep = ctx.getBean(WorkerGraphRep.class);
            Random random = new Random();
            Worker worker = new Worker();
            Group group = new Group();
            System.out.print("Input type of work");
            String type = in.next();
            group.setTypeOfWork(type);
            worker.setGroup(group);
            System.out.print("Input name");
            String name = in.next();
            worker.setName(name);
            System.out.print("Input surname");
            String surname = in.next();
            worker.setSurname(surname);
            System.out.print("Input old");
            Integer old = in.nextInt();
            worker.setOld(old);
            System.out.print("Input passport data");
            String data = in.next();
            worker.setPassportData(data);
            worker.setRegistration(random.nextBoolean());
            worker.setTeamLeader(random.nextBoolean());
            worker.setSalary(random.nextInt(200000)+15000);
            workerGraphRep.save(worker);
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean getWorkerById (ApplicationContext ctx) {
        try{
            WorkerGraphRep workerGraphRep = ctx.getBean(WorkerGraphRep.class);
            System.out.print("Input ID");
            Long id = in.nextLong();
            System.out.print(workerGraphRep.findOne(id).toString());
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean getWorkers (ApplicationContext ctx) {
        try{
            WorkerGraphRep workerGraphRep = ctx.getBean(WorkerGraphRep.class);
            Iterable<Worker> workers = workerGraphRep.findAll();
            for(Worker s : workers){
                System.out.print(s.toString()+"\n");
            }
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean updateWorker (ApplicationContext ctx) {
        try{
            WorkerGraphRep workerGraphRep = ctx.getBean(WorkerGraphRep.class);
            System.out.print("Input ID");
            Long id = in.nextLong();
            System.out.print("Input new name");
            String name = in.next();
            workerGraphRep.findOne(id).setName(name);
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }

    private boolean deleteWorker (ApplicationContext ctx) {
        try{
            WorkerGraphRep workerGraphRep = ctx.getBean(WorkerGraphRep.class);
            System.out.print("Input ID");
            Long id = in.nextLong();
            workerGraphRep.delete(id);
            return true;
        }
        catch (Exception ex) {
            System.out.print(ex.toString());
            return false;
        }
    }
    private void printMenu() {
        StringBuilder builder = new StringBuilder("\tSelect command\n");
        builder.append("1. Add worker\n");
        builder.append("2. Get all workers\n");
        builder.append("3. Get worker by id\n");
        builder.append("4. Update worker\n");
        builder.append("5. Delete worker by id\n");
        builder.append("q. Quit");
        System.out.println(builder.toString());
    }

}
