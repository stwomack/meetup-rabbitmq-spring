package com.rabbitmq.cftutorial.simple;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"rawtypes", "unchecked"})
@Repository("redisDao")
public class RedisDaoImpl implements RedisDao {

	@Autowired
	RedisTemplate redisTemplate;

	@Override
	public void saveToRedis(String channel, String message) throws Exception {
		String jsonString = new JSONObject(message).toString();
		System.out.println("Saving to redis: " + jsonString);
		redisTemplate.opsForValue().append(channel, jsonString);
	}
}
