package ie.gmit.sw.ai;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CipherBreaker {
	//console menu
	public static void menu() throws IOException {
		int choice = 0;
		Scanner console = new Scanner(System.in);
		
		System.out.println("Playfair Cipher:");
		System.out.println("================");
		System.out.println("1. Encrypt a file.");
		System.out.println("2. Decrypt a file with a key.");
		System.out.println("3. Decipher an encrypted file - simulated annealing algorithm.");
		System.out.println("4. Quit.");
		System.out.print("\nType Option [1-4]: ");
		
		choice = console.nextInt();
		while (choice != 4){
			switch (choice){
			case 1:
				//System.out.println("\nSelected 1.");
				listFiles(choice);
				choice = 4;
				System.out.println("\n");
				menu();
				break;
			case 2:
				//System.out.println("\nSelected 2.");
				listFiles(choice);
				choice = 4;
				System.out.println("\n");
				menu();
				break;
			case 3:
				//System.out.println("\nSelected 3.");
				listFiles(choice);
				choice = 4;
				System.out.println("\n");
				menu();
				choice = 4;
				break;
			default:
				System.out.println("\nInvalid input!");
				System.exit(0);
				break;
			}//switch
		}//while
		console.close();
	}//menu
	
	//lists all the files in a folder and depending on input executes either
	//encryption / decryption using a specific key / decryption - simulated annealing algorithm
	public static void listFiles(int selection) throws IOException{
		File folder = new File("files");
		File[] listOfFiles = folder.listFiles();
    	@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		int fileNum = 0;
		Playfair playfair = new Playfair();
		Filter filter = new Filter();
		FileHandler filehandler = new FileHandler();
		NGramHandler nGramHandler = new NGramHandler();
		Shuffler shuffler = new Shuffler();
		
		System.out.println("\nSelect a file:");
		System.out.println("================");
		
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
	    		System.out.println("File " + i + ": " + listOfFiles[i].getName());
	    		fileNum++;
	    	}//if
	    	else if (listOfFiles[i].isDirectory()) {
	    		System.out.println("Found Directory: " + listOfFiles[i].getName());
	    		System.out.println("Cannot access directory " + listOfFiles[i].getName() + ".");
	    	}//else if
	    }//for
	    
	    //if files exist in the files folder allow the user to select a file
	    if (fileNum != 0){
	    	System.out.print("File: ");
		    int choice = input.nextInt();
		    
		    if (!(choice > fileNum - 1)){
		    	System.out.println("\nSelected file: " + listOfFiles[choice].getName());
		    	//encrypt the file
		    	if (selection == 1){
		    		String path = "files/" + listOfFiles[choice].getName();
		    		String fileContent = filehandler.readFile(path, StandardCharsets.UTF_8);
		    		String filteredContent = filter.format(fileContent);
		    		filteredContent = filter.charSingleOccurance(filteredContent);
		    		//System.out.println(filteredContent);
		    		
		    		System.out.print("Enter an encryption key: ");
		    		String encKey = input.next();
		    		
		    		System.out.println("\nEncrypting please wait...");
		    		try {
						Thread.sleep(1000);
					}//try 
		    		catch (InterruptedException e) {
						e.printStackTrace();
					}//catch
		    		
		    		playfair.setKey(encKey);
		    		playfair.generateKey();
		    		playfair.generateMatrix();
		    		String encryptedText = playfair.encryptMessage(filteredContent);
		    		//System.out.println(encryptedText);
		    		
		    		System.out.println("\nEncryption complete, would you like to save the file?");
		    		System.out.println("1. Save.");
		    		System.out.println("2. Exit.");
		    		System.out.print("\nType Option [1-2]: ");
		    		int save = input.nextInt();
		    		
		    		if (save == 1){
		    			System.out.print("Enter file name: ");
		    			String name = input.next();
		    			filehandler.writeFile(name, encryptedText);
		    		}//if
		    		else if (save == 2){
		    			System.out.println("Exiting...");
		    		}//else if
		    		else {
		    			System.out.println("Invalid input!");
		    			System.exit(0);
		    		}//else
		    	}//if
		    	//decrypt the file
		    	else if (selection == 2){
		    		String path = "files/" + listOfFiles[choice].getName();
		    		String fileContent = filehandler.readFile(path, StandardCharsets.UTF_8);
		    		
		    		System.out.print("\nEnter a decryption key: ");
		    		String dencKey = input.next();
		    		
		    		System.out.println("\nDecrypting please wait...");
		    		try {
						Thread.sleep(1000);
					}//try 
		    		catch (InterruptedException e) {
						e.printStackTrace();
					}//catch
		    		
		    		playfair.setKey(dencKey);
		    		playfair.generateKey();
		    		playfair.generateMatrix();
		    		String decryptedText = playfair.decrypt(fileContent);
		    		decryptedText = filter.replaceX(decryptedText);
		    		//System.out.println(decryptedText);
		    		
		    		System.out.println("\nDecryption complete, would you like to save the file?");
		    		System.out.println("1. Save.");
		    		System.out.println("2. Exit.");
		    		System.out.print("\nType Option [1-2]: ");
		    		int save = input.nextInt();
		    		
		    		if (save == 1){
		    			System.out.print("Enter file name: ");
		    			String name = input.next();
		    			filehandler.writeFile(name, decryptedText);
		    		}//if
		    		else if (save == 2){
		    			System.out.println("Exiting...");
		    		}//else if
		    		else {
		    			System.out.println("Invalid input!");
		    			System.exit(0);
		    		}//else
		    	}//else if
		    	else if (selection == 3){
		    		double logProbability;
		    		double fitness = 0;
		    		double parentFitness;
		    		Map<String, Integer> gramMap = new HashMap<String, Integer>();
		    		
		    		//load 4grams.txt
		    		nGramHandler.loadHashMap();
		    		gramMap = nGramHandler.getGramMap();
		    		
		    		//generating a random key
		    		String alphabet = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
		    		alphabet = filter.format(alphabet);
		    		char[] chars = alphabet.toCharArray();
		    		char[] randKey = shuffler.generateRandomKey(chars);
		    		//System.out.println(randKey);
		    		
		    		//setting the key and generating the key + matrix in playfair class
		    		String key = new String(randKey);
		    		playfair.setKey(key);
		    		playfair.generateKey();
		    		playfair.generateMatrix();
		    		
		    		//getting the matrix generated in playfair class and setting
		    		//shuffler matrix to that
		    		char[][] matrix = playfair.getMatrix();
		    		shuffler.setMatrix(matrix);
		    		//System.out.println("\n");
		    		//shuffler.printMatrix();
		    		
		    		//set parent key in shuffler
		    		shuffler.setParent(playfair.getKey());
		    		
		    		//decrypt cipher text with key
		    		String path = "files/" + listOfFiles[choice].getName();
		    		String fileContent = filehandler.readFile(path, StandardCharsets.UTF_8);
		    		String decryptedText = playfair.decrypt(fileContent);
		    		//decryptedText = filter.replaceX(decryptedText);
		    		
		    		//System.out.println(fileContent);
		    		//System.out.println(decryptedText);
		    		
		    		//take the decrypted text and split into 4-grams / 4-Mers
		    		String[] mers = playfair.create4Mers(decryptedText);
		    		
		    		//take every 4gram and calculate key fitness of the randomly generated parent key		    		
		    		for (String s : mers){
		    			if (gramMap.keySet().contains(s)) {
		    				logProbability = (Math.log10(nGramHandler.getFreq(s)) / Math.log10(nGramHandler.getGramCount()));
		    				fitness += logProbability;
		    			}//if
		    		}//for
		    		parentFitness = fitness;
		    		System.out.println("Initial parent fitness: " + parentFitness);
		    		
		    		for (int temp = 10; temp > 0; temp--){
		    			for (int transitions = 50000; transitions > 0; transitions--){
				    		double childFitness;
				    		String childKey;
				    		double delta;
				    		double fitnessTest = 0;
				    		
				    		//shuffle parent
		    				childKey = shuffler.shuffleKey(shuffler.getParent());
		    				
		    				//decrypt the text using child key
				    		playfair.setKey(childKey);
				    		playfair.generateKey();
				    		playfair.generateMatrix();
		    				decryptedText = playfair.decrypt(fileContent);
		    				mers = playfair.create4Mers(decryptedText);
		    				
		    				//score fitness of child key
				    		for (String s : mers){
				    			if (gramMap.keySet().contains(s)) {
				    				logProbability = (Math.log10(nGramHandler.getFreq(s)) / Math.log10(nGramHandler.getGramCount()));
				    				fitnessTest += logProbability;
				    			}//if
				    		}//for
				    		childFitness = fitnessTest;
				    		
				    		//calculate delta
				    		delta = childFitness - parentFitness;
				    		//System.out.println("\nParent Fitness: " + parentFitness + " - " + "Child Fitness: " + childFitness);
				    		//System.out.println("\nDelta: " + delta);
				    		
				    		//if the child key is better
				    		if (delta > 0 ){
				    			//System.out.println("Found a better key!");
				    			//set the child key as the new parent
				    			shuffler.setParent(childKey);
				    			//set the child matrix as the parent matrix
				    			shuffler.setMatrix(playfair.getMatrix());
				    			//parent fitness now becomes childfitness
				    			parentFitness = childFitness;
				    			
				    			//set the closest matching key
				    			if (delta < playfair.getClosestMatch()){
				    				playfair.setClosestMatch(delta);
				    				System.out.println(playfair.getClosestMatch());
				    				playfair.setClosestMatchKey(childKey);
				    			}//if
				    		}//if
				    		else {
				    			//new key is worse
				    			//System.out.println("Found a worse key!");
				    			//have a chance of setting it to the worse key regardless
				    			double chance = Math.pow(Math.E, (delta/temp));
				    			if (chance > 0.5) {
				    				shuffler.setParent(childKey);
				    				shuffler.setMatrix(playfair.getMatrix());
				    				parentFitness = childFitness;
				    			}//if
				    		}//else
				    		
		    			}//for
		    		}//for
		    		
		    		System.out.println("\nClosest match: " + playfair.getClosestMatch());
		    		System.out.println("Key used: " + playfair.getClosestMatchKey());
		    		playfair.setKey(playfair.getClosestMatchKey());
		    		playfair.generateKey();
		    		playfair.generateMatrix();
		    		decryptedText = playfair.decrypt(fileContent);
		    		System.out.println("Decrypted text: " + decryptedText);
		    		
		    		System.out.println("\nDecryption complete, would you like to save the file?");
		    		System.out.println("1. Save.");
		    		System.out.println("2. Exit.");
		    		System.out.print("\nType Option [1-2]: ");
		    		int save = input.nextInt();
		    		
		    		if (save == 1){
		    			System.out.print("Enter file name: ");
		    			String name = input.next();
		    			filehandler.writeFile(name, decryptedText);
		    		}//if
		    		else if (save == 2){
		    			System.out.println("Exiting...");
		    		}//else if
		    		else {
		    			System.out.println("Invalid input!");
		    			System.exit(0);
		    		}//else
		    	}//else if
		    	else {
		    		System.out.println("\nSELECTION ERROR!");
		    		System.exit(0);
		    	}//else
		    }//if
		    else {
		    	System.out.println("Invalid input!");
		    	System.exit(0);
		    }//else
	    }//if
	    else {
	    	System.out.println("\nError: files folder is empty!.");
	    	System.exit(0);
	    }//else
	  //input.close();
	}//listFiles
	
	//quick test to see if encryption & decryption works @ given example
	//key = "THEQUICKBROWNFOXJUMPEDOVERTHELAZYDOGS"
	//plain-text = "ARTIFICIALINTELLIGENCE"
	public static void testExample() {
		Playfair playfair = new Playfair();
		Filter filter = new Filter();
		playfair.setKey("THEQUICKBROWNFOXJUMPEDOVERTHELAZYDOGS");
		playfair.generateKey();
		playfair.generateMatrix();
		//encrypted -> SIIOOBKCSMKOHQSLBAKDKH
		System.out.println("\n");
		System.out.println(playfair.encryptMessage("ARTIFICIALINTELLIGENCE"));
		//decrypted -> ARTIFICIALINTELLIGENCE
		System.out.println(filter.replaceX(playfair.decrypt(playfair.encryptMessage("ARTIFICIALINTELLIGENCE"))));
	}//testExample
	
	//quick test to see if shuffler is working correctly
	public static void testShuffler() {
		Shuffler shuffler = new Shuffler();
		Filter filter = new Filter();
		Playfair playfair = new Playfair();
		
		//generating a random key
		String alphabet = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
		alphabet = filter.format(alphabet);
		char[] chars = alphabet.toCharArray();
		char[] randKey = shuffler.generateRandomKey(chars);
		System.out.print("Randomly generated key: ");
		System.out.print(randKey);
		
		//setting the key and generating the key + matrix in playfair class
		String key = new String(randKey);
		playfair.setKey(key);
		playfair.generateKey();
		playfair.generateMatrix();
		
		//getting the matrix generated in playfair class and setting shuffler matrix
		char[][] matrix = playfair.getMatrix();
		shuffler.setMatrix(matrix);
		shuffler.setParent(playfair.getKey());
		System.out.println("\nKey Matrix: \n");
		shuffler.printMatrix();
		
		//testing shuffling methods
		//shuffler.shuffleKey(shuffler.getParent());
		//System.out.println("\n");
		//shuffler.printMatrix();
		
		//shuffler.swapRandomLetters();
		//System.out.println("\n");
		//shuffler.printMatrix();
		
		//shuffler.swapRandomRows();
		//System.out.println("\n");
		//shuffler.printMatrix();
		
		//shuffler.swapRandomCols();
		//System.out.println("\n");
		//shuffler.printMatrix();
		
		//shuffler.flipAllRows();
		//System.out.println("\n");
		//shuffler.printMatrix();
		
		//shuffler.flipAllCols();
		//System.out.println("\n");
		//shuffler.printMatrix();
		
		//shuffler.reverseKey();
		//System.out.println("\n");
		//shuffler.printMatrix();
	}//testShuffler
	
	public static void main(String[] args) throws IOException {
		//testExample();
		//testShuffler();
		menu();
	}//main
}//CipherBreaker