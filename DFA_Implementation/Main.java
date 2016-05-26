import java.util.LinkedList;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author danjean
 *
 */
public class Main {

	private static final Scanner SCANNER = new Scanner(System.in);

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		DFA<Object> dfa = new DFA<Object>();

        //Entering the alphabet - Begin
		System.out.println("Add the alphabet in one string: ");
        String alphabet = SCANNER.nextLine();

        char[] characters = alphabet.toCharArray();
        
        for (char c : characters) {
            dfa.addToAlphabet((Object)c);
		}
        //Entering the alphabet - End
        
        //Entering states - Begin
        System.out.println("Enter all states, one per line, in the format q0 n.\n"
        		+ "Where q0 is the name of the state and n means that it is NOT an accepting state.\n"
        		+ "Type f when it's an accepting/final state:\n"
        		+ "(Type -- in the last line to stop adding states.)");
        
        String input;
		do{
        	input = SCANNER.nextLine();
        	String[] inputArray = input.split(" ");
        	input = inputArray[0];
        	
        	if(!input.equals("--")){
	        	State state = new State(inputArray[0]);
	        	if(inputArray.length > 1 && inputArray[1].toLowerCase().equals("f")){
	        		state.setAcceptState(true);
	                state.setFinalState(true);
	        	}
	            dfa.addToStates(state);
	        }
        }while(!input.equals("--"));
        //Entering states - End
        
        //Entering transitions - Begin
        System.out.println("Enter all transitions, one per line, in the format q0 0 q1.\n"
        		+ "Where q0 is the name of the input state you added, 0 is the character and q1 the output state.\n"
        		+ "(Type -- in the last line to stop adding transitions.)");
        String transition;
        do{
        	transition = SCANNER.nextLine();
        	String[] inputArray = transition.split(" ");
        	if(inputArray.length != 3 && !inputArray[0].equals("--")){
	        	System.out.println("Type in the proper format.");

	        }else{
	        	//Add a shallow verifier of the number of transitions added. Ex.: #States*#CharactersInAlphabet = #Transitions
	        	transition = inputArray[0];
	        	
	        	if(!transition.equals("--")){
	        		Transition<Object> t = 
	        			new Transition<Object>(dfa.getStateByName(transition), inputArray[1], dfa.getStateByName(inputArray[2]));
	        		dfa.addTransition(t);
	        	}
	        }
        }while(!transition.equals("--"));
        //Entering transitions - End

		//Entering the string to compute and computing - Begin
        String choice;
        LinkedList<Object> stringList = new LinkedList<Object>();
        do{
	        System.out.println("\nAdd the string to be tested: ");
	        String stringToBeTested = SCANNER.nextLine();
	        
	        char[] stringToComputeArray = stringToBeTested.toCharArray();
	        
	        if(!stringList.isEmpty()) stringList.clear();
	        for (char c : stringToComputeArray) {
	            stringList.add((Object)c);
			}
	        
	        State result = dfa.calculateFinalState(dfa.getStateQ0(), stringList);
	        Boolean accepted = (result != null)?result.isAcceptState():false; 
	        System.out.println("The string " +stringToBeTested+ " was " + ((accepted)?"Accepted":"Rejected"));
	       
	        System.out.print("To finish the program type -- or c to continue: ");
	        choice = SCANNER.nextLine();
        }while(!choice.equals("--"));
        //Entering the string to compute and computing - End
        
        System.exit(0);
	}
}

