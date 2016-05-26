public class State {
    private boolean finalState = false;
    private boolean acceptState = false;
    private String name;
     
    public State (String name) {
    	this.name = name;
	}
	public State() {
	}
	
	public boolean isFinalState() {
        return finalState;
    }
    public void setFinalState(boolean finalState) {
        this.finalState = finalState;
    }
    public boolean isAcceptState() {
        return acceptState;
    }
    public void setAcceptState(boolean acceptState) {
        this.acceptState = acceptState;
    }
    
    public void setName(String name) {
		this.name = name;
	}
    
    public String getName(){
    	return name;
    }
}