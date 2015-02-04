package DataStructures;

/**
 * DocewordDictionary-oliota käytetään (koodaus, alkuperäinen bittijono) -parien
 * tallentamiseen. Luokka on toteutettu hajautuksen avulla, joten purkaessa
 * hyvin usein tehtävä "vastaako luettu bittijono jotain koodisanaa"-testaus
 * saadaan toimimaan mahdollisimman nopeasti.
 *
 * @author teemupitkanen1
 */
public class CodewordDictionary {

    private final DictionaryEntry[] encodings;

    private final int TABLE_SIZE = 500;

    private int size;

    public CodewordDictionary() {
        encodings = new DictionaryEntry[TABLE_SIZE];
        size = 0;
    }

    public void insertCodeword(String codeword, String bitstring) {
        DictionaryEntry newEntry = new DictionaryEntry(codeword, bitstring);
        int index = Math.abs(codeword.hashCode() % TABLE_SIZE);
        if (encodings[index] == null) {
            encodings[index] = newEntry;
        } else {
            DictionaryEntry prev = encodings[index];
            while (prev.getNext() != null) {
                prev = prev.getNext();
            }
            prev.setNext(newEntry);
        }
        size++;
    }

    public boolean containsCodeword(String codeword) {
        int index = Math.abs(codeword.hashCode() % TABLE_SIZE);
        if (encodings[index] == null) {
            return false;
        } else if (encodings[index].getCodeword().equals(codeword)) {
            return true;
        } else {
            DictionaryEntry entry = encodings[index];
            while (entry.getNext() != null) {
                entry = entry.getNext();
                if (entry.getCodeword().equals(codeword)) {
                    return true;
                }
            }
            return false;
        }
    }

    public String get(String codeword) {
        if (!containsCodeword(codeword)) {
            return null;
        }
        DictionaryEntry entry = encodings[Math.abs(codeword.hashCode() % TABLE_SIZE)];
        if (entry.getCodeword().equals(codeword)) {
            return entry.getBitstring();
        }
        while (entry.getNext() != null) {
            entry = entry.getNext();
            if (entry.getCodeword().equals(codeword)) {
                return entry.getBitstring();
            }
        }
        return null;
    }

    public int size() {
        return size;
    }
}
