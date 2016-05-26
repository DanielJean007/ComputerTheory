import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
 
public class DFA<T> {
    private Set<Object> alphabet = new HashSet<Object>();
    private Set<State> states = new HashSet<State>();
    private Set<Transition<T>> transitionFunction = new HashSet<Transition<T>>();
     
    public void addToAlphabet(Object symbol) {
        alphabet.add(symbol);
    }
     
    public void removeFromAlphabet(Object symbol){
        alphabet.remove(symbol);
    }
     
    public void addToStates(State state){
        states.add(state);
    }
 
    public void removeToState(State state){
        states.remove(state);
    }
     
    public void removeTransition(Transition<T> transition){
        transitionFunction.remove(transition);
    }
     
    public void addTransition(Transition<Object> t1) throws IllegalArgumentException{
        // no 2 outputs for same input+symbol
        if(transitionFunction.stream()
                .noneMatch(t -> t.getInputState().equals(t1.getInputState()) &&
                                t.getSymbol().equals(t1.getSymbol()))){
            transitionFunction.add((Transition<T>) t1);
        } else {
            throw new IllegalArgumentException();
        }
    }
     
    public Set<State> getAcceptStates(){
        return states.stream().filter(s -> s.isAcceptState())
                .collect(Collectors.toSet());
    }
 
    public State calculateFinalState(State state, LinkedList<Object> stringToTest)
                throws IllegalStateException, IllegalArgumentException {
        if(stringToTest.isEmpty()){
            return state;
        }

        State nextState = new State();
        for (int i = 0; i < stringToTest.size(); i++) {
        	if(alphabet.contains(stringToTest.peek())){
				nextState = getNextState(state, stringToTest.get(i).toString());
				if(nextState == null) return null;
				
				if (i == stringToTest.size()-1) return nextState;
				else state = nextState;
        	}else{
            	System.out.println("Illegal character!");
            	return null;
        	}
		}
        
        //If the automaton couldn't calculate at all, it throws an exception.
        //throw new IllegalStateException();
        return null;
    }
    
    private State getNextState(State state, Object object){
    	
		for (Transition<T> t : transitionFunction) {
			if(t.getInputState().equals(state))
				if(t.getSymbol().equals(object))
					return t.getOutputState();
		}
    	
		//In case the state or symbol hasn't been acquired.
		return null;
    }

	public State getStateByName(String transition) {
		for (State state : states) {
			if(state.getName().equals(transition)) return state;
		}
		
		//In case the given state is not in the set.
		return null;
	}
	
	public State getStateQ0(){
		Object[] obj = states.toArray(); 
		return (State) obj[0];
	}
}