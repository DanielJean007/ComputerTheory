public class Transition<T> {
    State input;
    Object symbol;
    State output;
     
    public Transition(State input, Object symbol, State output){
        this.input = input;
        this.symbol = symbol;
        this.output = output;
    }
    public State getInputState() {
        return input;
    }
 
    public Object getSymbol() {
        return symbol;
    }
 
    public State getOutputState() {
        return output;
    }
}