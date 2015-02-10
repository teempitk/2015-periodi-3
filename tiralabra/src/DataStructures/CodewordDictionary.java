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
     * joten täyttöaste on noin 1,5. Hyvä hajautustaulun koko on alkuluku ja
     * kaukana kahden potensseista, (2^8+2^9)/2=384.
     */
    private final int TABLE_SIZE = 383;
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
     * olevan käännöksen ylivuotolistan ensimmäiseksi.
     *
     * @param codeword Käännöksen koodisana (hajautustaulun avain).
     * @param bitstring Koodisanan käännös (hajautustaulun arvo).
     */
    public void insertCodeword(String codeword, String bitstring) {
        int index = hash(codeword);
        DictionaryEntry next = encodings[index];
        encodings[index] = new DictionaryEntry(codeword, bitstring, next);
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
        int index = hash(codeword);
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
        DictionaryEntry entry = encodings[hash(codeword)];
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

    /**
     * Laskee hajautusfunktion arvon koodisanalle.
     * @param codeword koodisana, jolle lasketaan.
     * @return Hajautusfunktion arvo.
     */
    private int hash(String codeword) {
        int index = (codeword.hashCode() % TABLE_SIZE);
        if (index < 0) {
            index += TABLE_SIZE;
        }
        return index;
    }

    /**
     * Palauttaa taulussa olevien käännösten lukumäärän.
     *
     * @return käännösten lkm.
     */
    public int size() {
        return size;
    }
}
