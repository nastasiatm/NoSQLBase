package ru.ifmo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.ifmo.CRUD.CRUDcass;
import ru.ifmo.CRUD.CRUDmongo;
import ru.ifmo.CRUD.CRUDneo;
import ru.ifmo.CRUD.CRUDredis;
import ru.ifmo.clients.MongoCli;
import ru.ifmo.clients.RedisCli;

import java.awt.image.ImagingOpException;
import java.io.BufferedReader;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration
@SpringBootApplication
public class App 
{
    private final MongoCli mongoCli;
    private final RedisCli redisCli;
    private static Scanner in = new Scanner(System.in);

    @Autowired
    public App(MongoCli mongoCli, RedisCli redisCli) {
        this.mongoCli = mongoCli;
        this.redisCli = redisCli;
    }

    public static void main( String[] args ) throws ImagingOpException
    {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

        CRUDmongo crud = new CRUDmongo();
        CRUDredis redis = new CRUDredis();
        CRUDneo neo = new CRUDneo();
        CRUDcass cass = new CRUDcass();
        while (true) {
            System.out.print("Select DB\n1. MongoDB\n2. Redis\n3. Neo4j\n4. Cassandra\nq. Exit\n");
            String command = in.nextLine();
            switch (command) {
                case "1":
                    crud.mongoLoop();
                    break;
                case "2":
                    redis.redisLoop();
                    break;
                case "3":
                    neo.neoLoop(context);
                    break;
                case "4":
                    cass.cassLoop(context);
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Not a command. Retry input");
                    break;
            }
        }
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx){
        return args->{
            /*RedisGenerate generate = ctx.getBean(RedisGenerate.class);
            generate.generate();
            MongoGenerate gen = ctx.getBean(MongoGenerate.class);
            gen.generate();
            Neo4jGenerator gen = ctx.getBean(Neo4jGenerator.class);
            gen.generate(ctx);
            CassandraGenerate gen = ctx.getBean(CassandraGenerate.class);
            gen.generate(ctx);*/

        };
    }
}
