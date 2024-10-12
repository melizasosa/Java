package OrderService.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@Configuration
@EnableJpaRepositories(basePackages = "OrderService.repository")
@EnableRedisRepositories(basePackages = "OrderService.redisRepository")
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public void guardarEnRedis(String key, String value, Integer exp) {
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,exp,TimeUnit.MINUTES);
    }

    public String getDataDesdeRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void borrarData(String key) {
        redisTemplate.delete(key);
    }

}
