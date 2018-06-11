package com.javatar.util;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;




@ManagedBean
@SessionScoped
public class MessageSender  implements Serializable {
 

 
	private static final long serialVersionUID = 7446542288815139342L;

	// URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server
	// is on localhost
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	// default broker URL is : tcp://localhost:61616"
	private static String subject = "q4_ahenk-gui_2read"; // Queue Name.You can
															// create any/many queue
															// names as per your
															// requirement.

	// public static void main(String[] args) throws JMSException {
	// connectAndSendMessage();
	// }
	public void connectAndSendMessage(String messageToBeSent) throws JMSException {
		// Getting JMS connection from the server and starting it
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		// Connection connection = connectionFactory.createConnection();
		Connection connection = connectionFactory.createConnection("admin", "anything");
		connection.start();

		// Creating a non transactional session to send/receive JMS message.
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Destination represents here our queue 'JCG_QUEUE' on the JMS server.
		// The queue will be created automatically on the server.
		Destination destination = session.createQueue(subject);

		// MessageProducer is used for sending messages to the queue.
		MessageProducer producer = session.createProducer(destination);

		// We will send a small text message saying 'Hello World!!!'
		TextMessage message = session.createTextMessage(
				"{\"type\":\"RESPONSE_AGREEMENT\",\"parameterMap\":{\"path\":\"/sample-agreementtttttttt.txt\",\"password\":\"1\",\"port\":22,\"host\":\"192.168.1.110\",\"username\":\"lider\"},\"protocol\":\"SSH\",\"timestamp\":1525257599245}");

 
		message = session.createTextMessage(
				"{\"type\":\"EXECUTE_TASK\",\"task\":{\"id\":1821,\"plugin\":{\"id\":366,\"name\":\"resource-usage\",\"version\":\"1.1\",\"description\":\"Lider Ahenk resource-usage plugin\",\"active\":true,\"deleted\":false,\"machineOriented\":true,\"userOriented\":false,\"policyPlugin\":false,\"taskPlugin\":true,\"usesFileTransfer\":false,\"xBased\":false,\"createDate\":1525766086000,\"modifyDate\":1527104786000},\"commandClsId\":\"RESOURCE_INFO_FETCHER\",\"parameterMap\":{},\"deleted\":false,\"cronExpression\":null,\"createDate\":1527501166264,\"modifyDate\":null,\"mailSend\":false},\"timestamp\":\"28-05-2018 12:52\",\"fileServerConf\":null}");
		message = session.createTextMessage(
				"{\"type\":\"EXECUTE_TASK\",\"task\":{\"id\":1806,\"plugin\":{\"id\":351,\"name\":\"antivirus\",\"version\":\"1.1\",\"description\":\"Lider Ahenk antivirus plugin\",\"active\":true,\"deleted\":false,\"machineOriented\":false,\"userOriented\":false,\"policyPlugin\":false,\"taskPlugin\":false,\"usesFileTransfer\":false,\"xBased\":false,\"createDate\":1525766080000,\"modifyDate\":1527104782000},\"commandClsId\":\"INSTANT_SCAN\",\"parameterMap\":{\"folderPath\":\"/home/deneme\"},\"deleted\":false,\"cronExpression\":null,\"createDate\":1527105520264,\"modifyDate\":null,\"mailSend\":false},\"timestamp\":\"23-05-2018 22:58\",\"fileServerConf\":null}");

		message= session.createTextMessage(messageToBeSent);
		
		
		// Here we are sending our message!
		producer.send(message);

		System.out.println("sentttttt  '" + message.getText() + "'");
		connection.close();
	}
}
