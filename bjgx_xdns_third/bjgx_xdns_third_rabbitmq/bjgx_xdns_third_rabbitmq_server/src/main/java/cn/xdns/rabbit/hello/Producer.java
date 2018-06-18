package cn.xdns.rabbit.hello;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
	
	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) {
		ConnectionFactory factory = new  ConnectionFactory();
		factory.setHost("localhost");
		Connection connection;
		try {
			connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello World!";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
			channel.close();
			connection.close();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	
//	public static void main(String[] args) {
//		
//		ConnectionFactory factory = new  ConnectionFactory();
//		factory.setUsername("guest");
//		factory.setPassword("guest");
//		factory.setHost("localhost");
//		try {
//			Connection conn = factory.newConnection();
//			//获取通道
//			Channel channel = conn.createChannel();
//			//声明交换器
//			String exchangeName = "hello-exchange";
//			channel.exchangeDeclare(exchangeName ,"direct", true);
//			//发布消息
//			String routingKey = "hola";
//			byte[] messageBodyBytes = "quit".getBytes();
//			channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);
//			channel.close();
//			conn.close();
//		} catch (IOException | TimeoutException e) {
//			e.printStackTrace();
//		}
//	
//	}
	
	
}
