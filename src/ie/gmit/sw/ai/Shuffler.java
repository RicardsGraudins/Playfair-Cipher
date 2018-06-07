package ie.gmit.sw.ai;

import java.util.Random;

//shuffles the key
public class Shuffler {
	private String parent;
	private char matrix[][] = new char[5][5];
	
	//generates a random key
	public char[] generateRandomKey (char[] key) {
		int index, temp;
		Random random = new Random();
		for (int i = key.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			temp = key[index];
			key[index] = key[i];
			key[i] = (char) temp;
		}//for
		return key;
	}//generateRandomKey
	
	//pick numbers at random and change characters inside the matrix using those numbers
	private void swapRandomLetters() {
		int row1 = (int) Math.floor((Math.random() * 5));
		int row2 = (int) Math.floor((Math.random() * 5));
		int col1 = (int) Math.floor((Math.random() * 5));
		int col2 = (int) Math.floor((Math.random() * 5));
		
		//make sure the numbers are not the same
		if (row1 == row2 || col1 == col2){
			swapRandomLetters();
		}//if
		else {
			char temp = matrix[row1][col1];
			matrix[row1][col1] = matrix[row2][col2];
			matrix[row2][col2] = temp;
		}//else
	}//swapRandomLetters
	
	//randomly picks 2 rows and switches them around in the matrix
	private void swapRandomRows() {
		int row1 = (int) Math.floor((Math.random() * 5));
		int row2 = (int) Math.floor((Math.random() * 5));
		
		if (row1 == row2){
			swapRandomRows();
		}//if
		else {
			char[] temp = matrix[row1];
			matrix[row1] = matrix[row2];
			matrix[row2] = temp;
		}//else
	}//swapRandomRows
	
	//randomly picks 2 columns and switches them around in the matrix
	private void swapRandomCols() {
		int col1 = (int) Math.floor((Math.random() * 5));
		int col2 = (int) Math.floor((Math.random() * 5));
		
		if (col1 == col2){
			swapRandomCols();
		}//if
		else {
			for (int i = 0; i < 5; i++){
				char temp = matrix[i][col1];
				matrix[i][col1] = matrix[i][col2];
				matrix[i][col2] = temp;
			}//for
		}//else
	}//swapRandomCols
	
	//take all the rows in the matrix and reverse them
	private void flipAllRows() {
		for (int i = 0; i < 5; i++) {
			char[] temp = matrix[i];
			
			for (int j = 0; j < temp.length / 2; j ++){
				char temp2 = temp[j];
				temp[j] = temp [temp.length - j - 1];
				temp[temp.length - j - 1] = temp2;
			}//for
			matrix[i] = temp;
		}//for
	}//flipAllRows
	
	//take all columns in the matrix and reverse them
	private void flipAllCols() {
		for (int i = 0; i < 5/2; i++) {
			for (int j = 0; j < 5; j++){
				char temp = matrix[i][j];
				matrix[i][j] = matrix[5 - i - 1][j];
				matrix[5 - i - 1][j] = temp;
			}//for
		}//for
	}//flipAllCols
	
	//reverse the entire matrix
	private void reverseKey() {
		flipAllRows();
		flipAllCols();
	}//reverseKey
	
	/*shuffle the key, 90% swap single letters
	*				    2% swap random rows
	*                   2% swap columns
	*                   2% flip all rows
	*                   2% flip all columns
	*                   2% reverse the whole key
	*/			    
	public String shuffleKey (String key) {
		int rand = (int)Math.floor(Math.random() * 100);
		
		switch (rand) {
		case 91:
			//System.out.println("Switching rows");
			swapRandomRows();
			break;
		case 92:
			//System.out.println("Switching rows");
			swapRandomRows();
			break;
		case 93:
			//System.out.println("Switching columns");
			swapRandomCols();
			break;
		case 94:
			//System.out.println("Switching columns");
			swapRandomCols();
			break;
		case 95:
			//System.out.println("Flipping rows");
			flipAllRows();
			break;
		case 96:
			//System.out.println("Flipping rows");
			flipAllRows();
			break;
		case 97:
			//System.out.println("Flipping columns");
			flipAllCols();
			break;
		case 98:
			//System.out.println("Flipping columns");
			flipAllCols();
			break;
		case 99:
			//System.out.println("Reversing key");
			reverseKey();
			break;
		case 100:
			//System.out.println("Reversing key");
			reverseKey();
			break;
		default:
			//System.out.println("Swapping random letters");
			swapRandomLetters();
			break;
		}//switch
			
		//System.out.println("Child key is: " + key);
		//child = key;
		
		//return the key that got shuffled as a string
		char[][] shuffledKey = this.getMatrix();
		String temp2 = "";
	    for (int i=0; i < shuffledKey.length; i++) {
	        for (int j=0; j < shuffledKey[i].length; j++) {
	            temp2 += shuffledKey[i][j];
	        }//for
	    }//for
		
		return temp2.toString();
	}//shuffleKey

	public String getParent() {
		return parent;
	}//getParent

	public void setParent(String parent) {
		this.parent = parent;
	}//setParent

	public char[][] getMatrix() {
		return matrix;
	}//getMatrix

	public void setMatrix(char[][] matrix) {
		this.matrix = matrix;
	}//setMatrix
	
	//print out the matrix
	public void printMatrix() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(matrix[i][j] + " ");
			}//for
			System.out.println();
		}//for
	}//printMatrix
}//Shuffler