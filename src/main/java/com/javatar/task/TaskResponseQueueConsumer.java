package com.javatar.task;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.javatar.util.FileReaderWriter;

public class TaskResponseQueueConsumer implements Runnable {


	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static String subject = "q4_server_2read";
	private MessageConsumer consumer;

	public String connectAndWaitForMessages() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection("admin", "anything");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination destination = session.createQueue(subject);

		MessageConsumer consumer = session.createConsumer(destination);

		String messageText = "no message received ";

		Message message = consumer.receive();

		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			System.out.println("Received message '" + textMessage.getText() + "'");

			messageText = textMessage.getText();
		} else {
			BytesMessage bm = (BytesMessage) message;
			byte data[] = new byte[(int) bm.getBodyLength()];
			bm.readBytes(data);
			System.out.println("Received messageee '" + new String(data) + "'");

			messageText = new String(data);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		connection.close();

		return messageText;

	}

	public TaskResponseQueueConsumer() {

		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			Connection connection;
			connection = connectionFactory.createConnection("admin", "anything");
			connection.start();

			// Creating session for seding messages
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Getting the queue 'JCG_QUEUE'
			Destination destination = session.createQueue(subject);

			// MessageConsumer is used for receiving (consuming) messages
			consumer = session.createConsumer(destination);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		Message message;
		try {
			message = consumer.receive();

			String messageText;
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				System.out.println("Received message '" + textMessage.getText() + "'");

				messageText = textMessage.getText();
			} else {
				BytesMessage bm = (BytesMessage) message;
				byte data[] = new byte[(int) bm.getBodyLength()];
				bm.readBytes(data);
				System.out.println("Received messageee '" + new String(data) + "'");

				messageText = new String(data);
			}

			FileReaderWriter fileReaderWriter = new FileReaderWriter();
			fileReaderWriter.writeToFile("/home/neval/file.txt", messageText);

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
