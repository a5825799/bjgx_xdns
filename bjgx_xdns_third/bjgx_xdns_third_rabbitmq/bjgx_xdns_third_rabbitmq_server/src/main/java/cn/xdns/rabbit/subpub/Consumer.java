package cn.xdns.rabbit.subpub;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Consumer {
	
	private static final String EXCHANGE_NAME = "customer";
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

	    try {
	    	Connection connection = factory.newConnection();
	    	Channel channel = connection.createChannel();
	    	channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
	    	String queueName = channel.queueDeclare().getQueue();
	    	System.out.println("我的对列名称是:{" + queueName + "}");
	    	channel.queueBind(queueName,EXCHANGE_NAME, "");
			channel.basicConsume(queueName,true,new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
			        System.out.println(" [x] Received '" + message + "'");
				}
				
			});
	    } catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	    
	}
	
}
