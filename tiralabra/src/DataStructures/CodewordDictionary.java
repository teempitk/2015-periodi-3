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

    /**
     * Kukin käännös tallennetaan DictionaryEntrynä tähän hajautustauluun (tai
     * sen ylivuotolistoihin).
     */
    private final DictionaryEntry[] encodings;
    /**
     * Hajautustaulun koko. Hajautustauluun laitetaan maksimissaan 257 entryä,
     * joten täyttöaste on noin puolet.
     */
    private final int TABLE_SIZE = 500;
    /**
     * CodewordDictionaryssa olevien käännösten lukumäärä.
     */
    private int size;

    /**
     * Konstruktori luo uuden hajautustaulun.
     */
    public CodewordDictionary() {
        encodings = new DictionaryEntry[TABLE_SIZE];
        size = 0;
    }

    /**
     * InsertCodeword-metodi lisää koodisanakäännöksen sanakirjaan. Koodisanat
     * sijoitetaan hajautustauluun String.hashCode-hajautusfunktion mukaisesti,
     * ja jos hajautustaulun paikka on varattu, menee uusi käännös paikassa
     * olevan käännöksen ylivuotolistaan.
     *
     * @param codeword Käännöksen koodisana (hajautustaulun avain).
     * @param bitstring Koodisanan käännös (hajautustaulun arvo).
     */
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

    /**
     * Metodi tarkistaa, löytyykö parametrina annetun koodisanan käännös
     * sanakirjasta.
     *
     * @param codeword Koodisana, jota kysely koskee.
     * @return true, jos sana on sanakirjassa. Muuten false.
     */
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

    /**
     * Palauttaa parametrina annettua koodisanaa vastaavan käännöksen.
     *
     * @param codeword Koodisana, jonka käännös halutaan
     * @return Koodisanaa vastaava käänös. Null, jos sanaa ei ole sanakirjassa.
     */
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
