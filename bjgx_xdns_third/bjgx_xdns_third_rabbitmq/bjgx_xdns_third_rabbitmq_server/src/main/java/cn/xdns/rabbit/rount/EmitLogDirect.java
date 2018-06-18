package cn.xdns.rabbit.rount;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {
	
	private static final String EXCHANGE_NAME = "direct_logs";
	
	public static void main(String[] args) throws IOException{
		String [] arr = {"info","\"Run. Run. Or it will explode.\""};
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;
		try {
			connection = factory.newConnection();
			Channel channel = connection.createChannel();
			//直接连接
			channel.exchangeDeclare(EXCHANGE_NAME, "direct");
			String severity = getSeverity(arr);
	        String message = getMessage(arr);
	        //对列名称  rountKey 是否持久化  要发送的信息
	        channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
	        System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
	        channel.close();
	        connection.close();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	private static String getSeverity(String[] strings){
		if (strings.length < 1)
			return "info";
		return strings[0];
	}
	
	private static String getMessage(String[] strings){
		if (strings.length < 2)
			return "Hello World!";
		return joinStrings(strings, " ", 1);
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
