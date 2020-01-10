package com.hy.ssm.lanxin.uitls;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class RedisConnRef {
    public void setConnectionFactory(JedisConnectionFactory connectionFactory) {
        RedisCache.setJedisConnectionFactory(connectionFactory);
    }
}
