package DataStructures;

/**
 *
 * @author teemupitkanen1
 */
public class DictionaryEntry {

    private final String bitString;

    private final String codeword;

    private DictionaryEntry next;

    public DictionaryEntry(String codeword, String bitstring) {
        this.bitString = bitstring;
        this.codeword = codeword;
    }

    public String getBitstring() {
        return bitString;
    }

    public void setNext(DictionaryEntry next) {
        this.next = next;
    }

    public DictionaryEntry getNext() {
        return next;
    }

    Object getCodeword() {
        return codeword;
    }

}
