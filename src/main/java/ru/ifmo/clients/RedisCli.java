package ru.ifmo.clients;


import lombok.Getter;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Created by anastasia on 19.04.17.
 */
@Component
public class RedisCli {
    @Getter
    private Jedis jedis;

    public RedisCli() {
        this.jedis = new Jedis("localhost", 6379, 1000*60);
    }

}
