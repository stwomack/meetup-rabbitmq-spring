package com.rabbitmq.cftutorial.simple;

import org.json.JSONObject;

public interface RedisDao {

	public abstract void saveToRedis(String channel, String message)
			throws Exception;

}