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
import ru.ifmo.clients.MongoCli;
import ru.ifmo.clients.RedisCli;

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

    @Autowired
    public App(MongoCli mongoCli, RedisCli redisCli) {
        this.mongoCli = mongoCli;
        this.redisCli = redisCli;
    }

    public static void main( String[] args )
    {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx){
        return args->{
            RedisGenerate generate = ctx.getBean(RedisGenerate.class);
            generate.generate();
            MongoGenerate gen = ctx.getBean(MongoGenerate.class);
            gen.generate();
        };
    }
}
