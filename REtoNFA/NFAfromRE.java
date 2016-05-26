package REtoNFA;

import java.util.Stack;

public class NFAfromRE {
	
	private char[] elements; 
	private String regex;	//Regular expression itself
	private int numStates;	//Number of states in the RE, also it represents the last state, in other words, the final state.
	
	public NFAfromRE(String regex){
		this.regex = regex;
		numStates = regex.length();
		elements = regex.toCharArray();
	}
	
	public Digraph REtoGraph() {
		Digraph graph;	//Graph we'll use to break down the Regular Expression
		
		graph = new Digraph(numStates + 1);	//Create the graph with the number of characters in the RegEX +1. This +1 is the added final state.
		Stack<Integer> posOpen = new Stack<Integer>();	//positions means the position of the open brackets or the operand '+'
		
		//Detecting positions
		for(int i=0; i < numStates; i++){
			int posClose = i;
			
			if(elements[i] == '(' || elements[i] == '+'){
				posOpen.push(i);
			}
			else if(elements[i] == ')'){
				//If my RE gives a closing bracket, I'll check Stack to check if it matches a opening bracket or a '+'
				int IsORoperand = posOpen.pop();
				
				if(elements[IsORoperand] == '+'){	//If the next element in the Stack is '+'
					posClose = posOpen.pop();
					
					//Adding edges for '+' operation
					graph.addEdge(posClose, IsORoperand+1);	
					graph.addEdge(IsORoperand, i);					
				}
				else
					posClose = IsORoperand;
			}
			
			//Star operation. If that's the case, we'll need 1 character lookahead
			if(i < numStates - 1 && elements[i+1] == '*'){
				graph.addEdge(posClose, i+1);
				graph.addEdge(i+1, posClose);
			}
			
			//For any other operation or symbol, we can just add one edge
			if(elements[i] == '(' || elements[i] == '*' || elements[i] == ')'){
				graph.addEdge(i, i+1);
			}
		}
		
		return graph;
	}
	
	/*
	* To check whether the RE accepts the string.
	* Naturally, we'll check that by running the string in the Digraph
	* we've created from the REtoGraph method 
	*/
	public boolean REaccepts(Digraph graph, String string) {
		Bag<Integer> bag = new Bag<Integer>();
		DirectedDFS dfs = new DirectedDFS(graph, 0);
		
		//Checking reachable positions in the graph.
		for(int vertex = 0; vertex < graph.V(); vertex++){
			if(dfs.marked(vertex)) bag.add(vertex);
		}
		
		//Computing how far can a character of the string go 
		for(int pos=0; pos < string.length(); pos++){
			Bag<Integer> states = new Bag<Integer>();
			
			for(int vertexes : bag){
				if(vertexes == numStates){
					continue;	//We don't know for sure we had a match, so we need to check the whole string
				}
				
				//Cheking whether we've found a match or an epsilon
				if(elements[vertexes] == string.charAt(pos) || elements[vertexes] == '&'){
					states.add(vertexes+1);
				}
				
				dfs = new DirectedDFS(graph, states);	//Check which states go to the final state.
				bag = new Bag<Integer>();
				for(int vertex = 0; vertex < graph.V(); vertex++){
					if(dfs.marked(vertex)){	//If this vertex has gotten to the next state
						bag.add(vertex);	//We add it to the collection.
					}
				}
			}
		}
		
		for(int vertex:bag){
			//Check if we've gotten any state that actually goes to the last state, aka final state 
			if(vertex == numStates) return true;
		}	
		
		return false;
	}
	
	public String parser(){
		String newRegEx = "";
		int numOpenParen = 0;
		int numORsign = 0;
		boolean closeParen = false;
		
		
		//Always add this to the newRegEx string.
		newRegEx+='(';
		numOpenParen++;
		
		for (int i = 0; i < elements.length; i++) {
			newRegEx+=elements[i];

			//When we see a + we open a parentheses
			if(elements[i] == '+'){
				newRegEx+='(';
				numOpenParen++;
				numORsign++;
			}
			
			if(elements[i] == '('){
				closeParen = false;
			}
			
			//When we see a ) we'll close some parentheses.
			//The number of parentheses we'll close is related to the number of + we've seen so far
			if(elements[i] == ')'){
				closeParen = true;
				while(numORsign > 0){
					newRegEx+=')';
					numOpenParen--;
					numORsign--;
				}
			}
			
			//The case where we've seen a ) and + still have ( to be closed.
			if(elements[i] == '+' && closeParen && numOpenParen > 0){
				closeParen = false;
				newRegEx+=')';
				numOpenParen--;
				numORsign--;
				char[] tmp = newRegEx.toCharArray();
				tmp[tmp.length-3] = ')';
				tmp[tmp.length-2] = '+';
				tmp[tmp.length-1] = '(';
				newRegEx = "";
				for (char c : tmp) {
					newRegEx+=c;
				}
			}
			
			//In case we're at the end of the string and we still have ( without a matching ) 
			if(i==elements.length-1 && numOpenParen > 0){
				while(numOpenParen > 0){
					newRegEx+=')';
					numOpenParen--;
				}
			}
		}
		
		//Updating global variables' values
		numStates = newRegEx.length();
		elements = newRegEx.toCharArray();
		
		return newRegEx;
	}
}
