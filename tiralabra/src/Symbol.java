
public class Symbol implements Comparable<Symbol> {

    private final char s;
    private int freq;

    public Symbol(char s, int freq) {
        this.freq = freq;
        this.s = s;
    }

    public int getFrequency() {
        return freq;
    }

    @Override
    public int compareTo(Symbol symbol) {
        return freq - symbol.getFrequency();
    }

    public char getSymbol() {
        return s;
    }
}
