
/**
 * Luokka Symbol vastaa yksinkertaisesti yhtä kirjainmerkkiä (char). Luokka
 * sisältää tiedon merkin tyypistä, sen esiintymiskertojen määrästä ja välineet
 * kahden Symbol-olion vertailuun.
 *
 * @author teemupitkanen1
 */
public class Symbol implements Comparable<Symbol> {

    /**
     * Luokkaa vastaava char-merkki
     */
    private final char s;
    /**
     * Merkin esiintymiskertojen määrä pakattavassa tekstissä.
     */
    private int freq;

    /**
     * Konstruktori alustaa merkin tyypin ja määrän parametrien mukaisesti
     *
     * @param s Kertoo, mitä merkkiä luotava olio vastaa
     * @param freq Merkin määrää tekstissä
     */
    public Symbol(char s, int freq) {
        this.freq = freq;
        this.s = s;
    }

    /**
     * Palauttaa merkin esiintymiskertojen määrän.
     *
     * @return
     */
    public int getFrequency() {
        return freq;
    }

    /**
     * Tarjoaa mahdollisuuden merkkien vertailuun esiintymiskertojen määrän
     * pohjalta.
     *
     * @param symbol
     * @return
     */
    @Override
    public int compareTo(Symbol symbol) {
        return freq - symbol.getFrequency();
    }

    /**
     * Palauttaa oliota vastaavan merkin (char).
     *
     * @return
     */
    public char getSymbol() {
        return s;
    }
}
