package ie.gmit.sw.ai;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NGramHandler {
	private Map <String, Integer> gramMap = new HashMap<String, Integer>();
	private long gramCount = 4224127912L;
	private static final String FILE_PATH = "4grams.txt";
	
	//populate the hashmap using 4grams.txt
	public void loadHashMap() throws IOException {
		FileHandler filehandler = new FileHandler();
		gramMap = filehandler.read4grams(FILE_PATH);
	}//loadHashMap

	public long getGramCount() {
		return gramCount;
	}//getGramCount

	public void setGramCount(long gramCount) {
		this.gramCount = gramCount;
	}//setGramCount

	public Map<String, Integer> getGramMap() {
		return gramMap;
	}//getGramMap
	
	public double getFreq (String s) {
			return gramMap.get(s);
	}//getFreq
}//NGramHandler