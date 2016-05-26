package REtoNFA;

import java.util.ArrayList;


public class Parser {
	String insertParentheses(String string){
		Tokenizer tokens = new Tokenizer();
		ArrayList<String> token = new ArrayList<String>();
		token = tokens.tokenize(string);
		
		for (String string2 : token) {
			System.out.println(string2);
		}
		
		return string;
	}

}	
