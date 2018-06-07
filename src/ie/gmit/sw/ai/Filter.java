package ie.gmit.sw.ai;

public class Filter {
	
	//formats string, changing all characters to uppercase and removing all special characters and numbers
    public String format(String s) {
		String filteredContent = s.toUpperCase().replaceAll("[^A-Za-z0-9]", "");
		filteredContent = filteredContent.replaceAll("[0-9]", "");
    	return filteredContent;
    }//filter
    
	//replaces the second occurence of a letter with an X e.g. LETTERKENNY -> LETXERKENXY
	public String charSingleOccurance(String s) {
		StringBuilder result = new StringBuilder();
		char last = '\u0000';
		
		//loop over every character in the string
		for (char c : s.toCharArray()) {
		     if (last == '\u0000' || c != last) {
		    	 result.append(c);
		    	 last = c;
		    }//if	
		    else {
		    	result.append("X");
		 	}//else
		}//for
		return result.toString();
	}//charSingleOccurance
	
	//reverts charSingleOccurance e.g. LETXERKENXY -> LETTERKENNY
	public String replaceX(String s) {
		StringBuilder result = new StringBuilder();
		
		//loop over every character in the string
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if (c == 'X'){
				result.append(s.charAt(i - 1));
			}//if
			else {
				result.append(c);
			}//else
		}//for
		return result.toString();
	}//replaceX
}//Filter