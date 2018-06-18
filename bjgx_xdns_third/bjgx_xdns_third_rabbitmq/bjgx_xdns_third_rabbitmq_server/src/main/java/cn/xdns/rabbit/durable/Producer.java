package cn.xdns.rabbit.durable;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Producer {
	
	private final static String QUEUE_NAME = "task_hello";
	
	public static void main(String[] args) {
		String [] arr = {"Work.java","dkkdsadsa"};
		ConnectionFactory factory = new  ConnectionFactory();
		factory.setHost("localhost");
		Connection connection;
		try {
			connection = factory.newConnection();
			Channel channel = connection.createChannel();
			boolean durable = true;  // 开启消息持久化
			channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
			String message = getMessage(arr);
			for (int i = 0; i < 10; i++) {
				//MessageProperties.PERSISTENT_TEXT_PLAIN 开启持久化
				channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, (message+i).getBytes());
			}
			
			System.out.println(" [x] Sent '" + message + "'");
			channel.close();
			connection.close();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	private static String getMessage(String [] args){
		if(args.length < 1) {
			return "Hello World";
		}
		return joinString(args," ");
	};	
	
	private static String joinString(String[] args, String delimiter) {
		int length = args.length;
		if  (length == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			builder.append(delimiter).append(args[i]);
		}
		return builder.toString();
	}

}
