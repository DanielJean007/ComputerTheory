package REtoNFA;

import java.util.Scanner;

public class Main {
	 private static Scanner sc;

	 public static void main(String[] args) {
    	sc = new Scanner(System.in);
    	
    	System.out.println("Welcome to be most beautiful and well written program ever.\n"
    			+ "The authors of this piece of art are: Daniel Jean and Emanuela Gomes.\n"
    			+ "Enjoy!\n\n\n");
    	
    	System.out.println("Before entering you Regular Expession:\n"
    			+ "Remember that OR is represented by '+' and that EPSILON is represented by '&'\n"
    			+ "Also, please does not add an empty space between characters unless it's in the alphabet of your RE. Thanks.\n"
    			+ "Please, place concatenation operations alongside OR operations. Ex: (-+&)(0+1)+(-+&)(2+3), rather then (-+&)((0+1)+(2+3)). It's a bug. I know.\n"
    			+ "You can finally enter your Regular Expression:");
    	String regexp = "(" + sc.nextLine() + ")";
    	
    	System.out.println("\n\nBefore typing the string to be tested:\n"
    			+ "Remember, does not type any empty space between characters unless it's in the alphabet of your RE.\n"
    			+ "Also, please does not add any of these characters to your string: '(', ')', '+', or '*'. Thanks.\n"
    			+ "Sorry for the inconvenience.\n"
    			+ "You can finally type the string to be tested:");
    	String txt = sc.nextLine();
    	
    	
        NFAfromRE nfa = new NFAfromRE(regexp);
        
        nfa.parser();
        Digraph graph = nfa.REtoGraph();
        StdOut.println(nfa.REaccepts(graph, txt));
        
        
    }

}
