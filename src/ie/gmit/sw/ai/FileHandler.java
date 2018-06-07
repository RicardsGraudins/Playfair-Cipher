package ie.gmit.sw.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileHandler {
	
	//read file and convert content into string
	public String readFile(String path, Charset encoding)  throws IOException {
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}//readFile
	
	//writes file to folder "files"
	public void writeFile(String fileName, String text) throws IOException {
		 File file = new File("files/" + fileName + ".txt");
	      file.createNewFile();
	      FileWriter writer = new FileWriter(file); 
	      
	      writer.write(text); 
	      writer.flush();
	      writer.close();
	      System.out.println("File saved as: " + fileName + ".txt");
	}//writeFile
	
	//reads in the 4grams file into a hashmap
	public HashMap<String, Integer> read4grams(String path) throws IOException {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
	    String line;
	    BufferedReader reader = new BufferedReader(new FileReader(path));
	    while ((line = reader.readLine()) != null)
	    {
	    	String[] columns = line.split(" ");
	    	if (columns.length >= 2) {
	    		String key = columns[0];
	    		int freq = Integer.parseInt(columns[1]);
	    		map.put(key, freq);
	    	}//if
	    	else {
	    		System.out.println("ignoring line: " + line);
	    	}//else
	    }//while
	    reader.close();	
	    return map;
	}//read4grams
}//FileHandler