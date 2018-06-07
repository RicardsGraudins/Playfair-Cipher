package ie.gmit.sw.ai;

import java.awt.Point;

//can both encrypt + decrypt: using 1 class instead of encryptor + decryptor since both use shared methods
public class Playfair {
	private String keyWord;
	private String key;
	private char matrix[][] = new char [5][5];
	private double closestMatch = 1;
	private String closestMatchKey;
	Filter filter = new Filter();
	
	//set the key - 25 unique letters
	public void setKey(String k) {
        String K_adjust = new String();
        boolean flag = false;
        K_adjust = K_adjust + k.charAt(0);
        for (int i = 1; i < k.length(); i++) {
            for (int j = 0; j < K_adjust.length(); j++) {
                if (k.charAt(i) == K_adjust.charAt(j)) {
                    flag = true;
                }//if
            }//for
            if (flag == false) {
                K_adjust = K_adjust + k.charAt(i);
            }//if
            flag = false;
        }//for
        keyWord = K_adjust;
		keyWord = keyWord.replace("J", "");
	}//setKey
	
    //generate the key - add alphabet to end of key excluding j
    public void generateKey() {
        boolean flag = true;
        char current;
        key = keyWord;
        for (int i = 0; i < 26; i++) {
            current = (char) (i + 97);
            if (current == 'j')
                continue;
            for (int j = 0; j < keyWord.length(); j++) {
                if (current == keyWord.charAt(j)) {
                    flag = false;
                    break;
                }//if
            }//for
            if (flag)
                key = key + current;
            flag = true;
        }//for
        //System.out.println(key + "\n");
    }//generateKey
    
    //generate the matrix using key
    public void generateMatrix() {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = key.charAt(counter);
                counter++;
            }//for
        }//for
    }//generateMatrix
    
    //create diagraphs from string
    private String[] createDiagraphs(String s) {
    	s = filter.format(s);
    	s = filter.charSingleOccurance(s);
        int size = s.length();
        if (size % 2 != 0) {
            size++;
            s = s + 'X';
        }//if
        String x[] = new String[size / 2];
        int counter = 0;
        for (int i = 0; i < size / 2; i++) {
            x[i] = s.substring(counter, counter + 2);
            counter = counter + 2;
        }//for
        return x;
    }//createDiagraphs
    
    //creates as many 4-Mers as it can from string
    public String[] create4Mers (String s) {
    	int size = s.length();
    	String x[] = new String[size / 4];
    	int counter = 0;
    	for (int i = 0; i < size / 4; i++) {
    		x[i] = s.substring(counter, counter + 4);
    		counter = counter + 4;
    	}//for
    	return x;
    }//create4Mers
    
    //get proper dimensions
    public int[] getDimensions(char letter) {
        int[] key = new int[2];
        if (letter == 'j'){
            letter = 'i';
        }//if
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == letter) {
                    key[0] = i;
                    key[1] = j;
                    break;
                }//if
            }//for
        }//for
        return key;
    }//getDimensions
    
    //encrypting the text
    public String encryptMessage(String Source) {
        String src_arr[] = createDiagraphs(Source);
        String Code = new String();
        char one;
        char two;
        int part1[] = new int[2];
        int part2[] = new int[2];
        for (int i = 0; i < src_arr.length; i++) {
            one = src_arr[i].charAt(0);
            two = src_arr[i].charAt(1);
            part1 = getDimensions(one);
            part2 = getDimensions(two);
            if (part1[0] == part2[0]) {
                if (part1[1] < 4)
                    part1[1]++;
                else
                    part1[1] = 0;
                if (part2[1] < 4)
                    part2[1]++;
                else
                    part2[1] = 0;
            }//if
            else if (part1[1] == part2[1]) {
                if (part1[0] < 4)
                    part1[0]++;
                else
                    part1[0] = 0;
                if (part2[0] < 4)
                    part2[0]++;
                else
                    part2[0] = 0;
            }//else if
            else {
                int temp = part1[1];
                part1[1] = part2[1];
                part2[1] = temp;
            }//else
            Code = Code + matrix[part1[0]][part1[1]] + matrix[part2[0]][part2[1]];
        }//for
        return Code;
    }//encryptMessage
    
    //decrypts the encrypted message
    public String decrypt(String out) {
		String decoded = "";
		for(int i = 0; i < out.length() / 2; i++) {
	        char a = out.charAt(2*i);
	        char b = out.charAt(2*i+1);
	        int r1 = (int) getPoint(a).getX();
	        int r2 = (int) getPoint(b).getX();
	        int c1 = (int) getPoint(a).getY();
	        int c2 = (int) getPoint(b).getY();
	        if(r1 == r2){
				c1 = (c1 + 4) % 5;
				c2 = (c2 + 4) % 5;
	        }//if 
	        else if (c1 == c2){
		        r1 = (r1 + 4) % 5;
		        r2 = (r2 + 4) % 5;
	        }//else if
	        else {
		        int temp = c1;
		        c1 = c2;
		        c2 = temp;
	        }//else
	        decoded = decoded + matrix[r1][c1] + matrix[r2][c2];
		}//for
		return decoded;
    }//decrypt
    
    //returns a point containing the row and column of the letter
    public Point getPoint(char c) {
	    Point pt = new Point(0,0);
	    for(int i = 0; i < 5; i++)
	    	for(int j = 0; j < 5; j++) {
	    		if(c == matrix[i][j])
	    		pt = new Point(i,j);
	    	}//for
	    return pt;
    }//point

	public char[][] getMatrix() {
		return matrix;
	}//getMatrix

	public void setMatrix(char[][] matrix) {
		this.matrix = matrix;
	}//setMatrix

	public String getKey() {
		//"properKey" is just the key, ensuring it has only 25 characters since
		//alphabet is added to it in generateKey()
		StringBuilder properKey = new StringBuilder();
		for (int i = 0; i < 25; i++){
			char c = key.charAt(i);
			properKey.append(c);
		}//for
		return properKey.toString();
	}//getKey

	public double getClosestMatch() {
		return closestMatch;
	}//getClosestMatch

	public void setClosestMatch(double closestMatch) {
		this.closestMatch = closestMatch;
	}//setClosestMatch

	public String getClosestMatchKey() {
		return closestMatchKey;
	}//getClosestMatchKey

	public void setClosestMatchKey(String closestMatchKey) {
		this.closestMatchKey = closestMatchKey;
	}//setClosestMatchKey
}//Playfair