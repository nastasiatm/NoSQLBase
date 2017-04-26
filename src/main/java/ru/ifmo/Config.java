package ru.ifmo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.ifmo.clients.MongoCli;
import ru.ifmo.clients.RedisCli;

/**
 * Created by anastasia on 19.04.17.
 */




@Configuration
@EnableTransactionManagement
@ComponentScan
public class Config {
    private final String HOST = "localhost";

    @Bean
    public RedisCli redisClient() {
        return new RedisCli();
    }

    @Bean
    public MongoCli mongoCli() {
        return new MongoCli(HOST, 27017);
    }
}

