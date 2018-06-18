package cn.xdns.rabbit.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {
	
	private static final String EXCHANGE_NAME = "topic_logs";
	
	public static void main(String[] args) {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = null;
		Channel channel = null;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
			
			String routingKey = getRouting(args);
			
			String message = getMessage(args);
			channel.basicPublish(EXCHANGE_NAME, routingKey,null, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
			
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	}
	
	public static String getRouting(String [] args) {
		if(args.length < 1) {
			return "anonymous.info";
		}
		return args[0];
	}

	private static String getMessage(String [] args) {
		if(args.length < 2) {
			return "Hello World";
		}
		return joinStrings(args," ",1);
	}
	
	private static String joinStrings(String[] strings, String delimiter, int startIndex) {
		int length = strings.length;
		if (length == 0 ) return "";
		if (length < startIndex ) return "";
		StringBuilder words = new StringBuilder(strings[startIndex]);
		for (int i = startIndex + 1; i < length; i++) {
		words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
	
	
}
