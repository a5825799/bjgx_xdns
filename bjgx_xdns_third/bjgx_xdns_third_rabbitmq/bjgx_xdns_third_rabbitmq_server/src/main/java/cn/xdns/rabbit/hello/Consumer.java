package cn.xdns.rabbit.hello;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Consumer {
	
	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

	    try {
	    	Connection connection = factory.newConnection();
	    	Channel channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body,"UTF-8");
					System.out.println("[x] Received'" + message + "'");
				}
			});
	    } catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	
	
//	public static void main(String[] args) {
//		ConnectionFactory factory = new ConnectionFactory();
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        factory.setHost("localhost");
//        //建立到代理服务器到连接
//        Connection conn;
//		try {
//			conn = factory.newConnection();
//			//获得信道
//	        final Channel channel = conn.createChannel();
//	        //声明交换器
//	        String exchangeName = "hello-exchange";
//	        channel.exchangeDeclare(exchangeName, "direct", true);
//	        //声明队列
//	        String queueName = channel.queueDeclare().getQueue();
//	        String routingKey = "hola";
//	        //绑定队列，通过键 hola 将队列和交换器绑定起来
//	        channel.queueBind(queueName, exchangeName, routingKey);
//	        while (true) {
//	        	boolean autoAck = false;
//	        	String consumerTag = "";
//	        	channel.basicConsume(queueName,autoAck,consumerTag, new DefaultConsumer(channel) {
//					
//	        		@Override
//					public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
//							byte[] body) throws IOException {
//	        			String routingKey = envelope.getRoutingKey();
//	        			String contentType = properties.getContentType();
//	                    System.out.println("消费的路由键：" + routingKey);
//	                    System.out.println("消费的内容类型：" + contentType);
//	                    long deliveryTag = envelope.getDeliveryTag();
//	                    //确认消息
//	                    channel.basicAck(deliveryTag, false);
//	                    System.out.println("消费的消息体内容：");
//	                    String bodyStr = new String(body, "UTF-8");
//	                    System.out.println(bodyStr);
//	        		}
//	        		
//	        	});
//			}
//		} catch (IOException | TimeoutException e) {
//			e.printStackTrace();
//		}
//	}
	
}
