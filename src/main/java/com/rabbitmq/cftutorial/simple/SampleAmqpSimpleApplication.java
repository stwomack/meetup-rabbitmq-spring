package com.rabbitmq.cftutorial.simple;

import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleAmqpSimpleApplication {

	private static final String MY_QUEUE = "javaAmqpMessages";

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	private AmqpAdmin amqpAdmin;

	@PostConstruct
	public void setUpQueue() {
	    Queue queue = new Queue(MY_QUEUE);
	    this.amqpAdmin.declareQueue(queue);
		TopicExchange exchange = new TopicExchange("chatExchange");
		this.amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("*"));
	}
	
	@Bean
	public SimpleMessageListenerContainer container() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(
				this.connectionFactory);
		Object listener = new Object() {
			@SuppressWarnings("unused")
			public void handleMessage(Object foo) throws JSONException, UnsupportedEncodingException {
				byte[] boo = (byte[]) foo;
				String str1 = new String(boo, "UTF-8");
				JSONObject jsonObject = new JSONObject(str1);
				System.out.println(jsonObject);
			}
		};
		MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
		container.setMessageListener(adapter);
		container.setQueueNames(MY_QUEUE);
		return container;
	}

}
