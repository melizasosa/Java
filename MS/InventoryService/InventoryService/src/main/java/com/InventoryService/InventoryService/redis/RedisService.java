package com.InventoryService.InventoryService.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Configuration
@EnableJpaRepositories(basePackages = "OrderService.repository") // JPA repositories
@EnableRedisRepositories(basePackages = "OrderService.redisRepository") // Redis repositories
public class RedisService {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public void guardarEnRedis(String key, String value, Integer exp) {
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,exp, TimeUnit.MINUTES);
    }

    public String getDataDesdeRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void borrarData(String key) {
        redisTemplate.delete(key);
    }

}
