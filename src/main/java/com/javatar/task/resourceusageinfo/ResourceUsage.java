package com.javatar.task.resourceusageinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.jms.JMSException;

import com.javatar.messaging.TaskStatusMessageImpl;
import com.javatar.util.FileReaderWriter;
import com.javatar.util.JsonReader;
import com.javatar.util.MessageSender;

@ManagedBean
@SessionScoped
public class ResourceUsage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String messageToBeSent = "";
	private String messageReceived = "";

	private List<ResourceUsageInfo> resourceUsageInfoList;

	public boolean outputText, outputText2, dataTable = false;

	public void sendAndReceive() {

		sendMessage();

		TaskStatusMessageImpl message = receiveMessage();

		this.setMessageReceived(message.getResponseMessage());
		fillTheListOfResponses(message);

		setVisibilities();
	}

	private void setVisibilities() {

		outputText = true;
		outputText2 = true;
		dataTable = true;

	}

	private void fillTheListOfResponses(TaskStatusMessageImpl message) {
		Map<String, Object> map = message.getResponseData();

		resourceUsageInfoList = new ArrayList<ResourceUsageInfo>();

		ArrayList<Object> keys = new ArrayList<Object>(map.keySet());
		ArrayList<Object> values = new ArrayList<Object>(map.values());

		for (int i = 0; i < keys.size(); i++) {
			ResourceUsageInfo resourceUsageInfo = new ResourceUsageInfo(keys.get(i).toString(),
					values.get(i).toString());
			resourceUsageInfoList.add(resourceUsageInfo);

		}
	}

	public void sendMessage() {

		MessageSender messageSender = new MessageSender();
		try {
			messageSender.connectAndSendMessage(messageToBeSent);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	private TaskStatusMessageImpl receiveMessage() {

		String messageText = null;

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FileReaderWriter fileReaderWriter = new FileReaderWriter();
		messageText = fileReaderWriter.readFromFile("/home/neval/file.txt");

		JsonReader reader = new JsonReader();
		TaskStatusMessageImpl taskStatusMessage = (TaskStatusMessageImpl) reader.read(messageText,
				TaskStatusMessageImpl.class);

		return taskStatusMessage;
	}

	public String getMessageToBeSent() {
		return messageToBeSent;
	}

	public void setMessageToBeSent(String messageToBeSent) {
		this.messageToBeSent = messageToBeSent;
	}

	public String getMessageReceived() {
		return messageReceived;
	}

	public void setMessageReceived(String messageReceived) {
		this.messageReceived = messageReceived;
	}

	public boolean isOutputText() {
		return outputText;
	}

	public void setOutputText(boolean outputText) {
		this.outputText = outputText;
	}

	public boolean isOutputText2() {
		return outputText2;
	}

	public void setOutputText2(boolean outputText2) {
		this.outputText2 = outputText2;
	}

	public boolean isDataTable() {
		return dataTable;
	}

	public void setDataTable(boolean dataTable) {
		this.dataTable = dataTable;
	}

	public List<ResourceUsageInfo> getResourceUsageInfoList() {
		return resourceUsageInfoList;
	}

	public void setResourceUsageInfoList(List<ResourceUsageInfo> resourceUsageInfoList) {
		this.resourceUsageInfoList = resourceUsageInfoList;
	}

}