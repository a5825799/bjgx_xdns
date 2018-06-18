package cn.xdns.rabbit.rount;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceiveLogsDirect {
	
	
	private static final String EXCHANGE_NAME = "direct_logs";
	
	public static void main(String[] args) throws IOException,TimeoutException{
		String [] arr = {"error","warning","info"};
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		String queueName = channel.queueDeclare().getQueue();
		
		if(arr.length < 1) {
			System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
			System.exit(1);
		}
		
		for(String severity : arr){
			channel.queueBind(queueName, EXCHANGE_NAME, severity);
	    }
		
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		channel.basicConsume(queueName, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
                 AMQP.BasicProperties properties, byte[] body) throws IOException {
			String message = new String(body, "UTF-8");
			System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
	    });
		
	}
	
	
	
}
