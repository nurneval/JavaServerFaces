package com.javatar.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderWriter {

	public void writeToFile(String filePath, String content) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

			bw.write(content);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public String readFromFile(String filePath) {
		String fileContent = null;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String sCurrentLine;
			StringBuilder stringBuilder = new StringBuilder();

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				stringBuilder.append(sCurrentLine);

			}
			fileContent= stringBuilder.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

}
