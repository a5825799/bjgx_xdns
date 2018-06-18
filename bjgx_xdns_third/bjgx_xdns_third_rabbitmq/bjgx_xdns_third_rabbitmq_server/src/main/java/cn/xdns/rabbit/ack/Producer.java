package cn.xdns.rabbit.ack;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
	
	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) {
		String [] arr = {"Work.java","dkkdsadsa"};
		ConnectionFactory factory = new  ConnectionFactory();
		factory.setHost("localhost");
		Connection connection;
		try {
			connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = getMessage(arr);
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
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
