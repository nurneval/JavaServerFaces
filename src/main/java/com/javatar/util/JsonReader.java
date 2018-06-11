package com.javatar.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class JsonReader {

	public Object read(String msg,Class<?>  clazz) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm"));

		Object message = null;
		try {
			message = mapper.readValue(msg, clazz );
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
}
