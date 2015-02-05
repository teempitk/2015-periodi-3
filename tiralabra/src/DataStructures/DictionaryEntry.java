package DataStructures;

/**
 * DictionaryEntry on yksi alkio CodewordDictionary-hajautustaulussa.
 *
 * @author teemupitkanen1
 */
public class DictionaryEntry {

    /**
     * bitString on käännösalkion arvo.
     */
    private final String bitString;
    /**
     * codeword on käännösalkion avain.
     */
    private final String codeword;
    /**
     * Hajautustaulun ylivuotolistat tallennetaan alkioiden next-kentän avulla.
     * Jos hajautustaulu vuotaa uutta käännöstä lisätessä yli indeksissä i,
     * uuden käännöksen DictionaryEntry asetetetaan i:ssä olevan entryn
     * nextiksi. Jos ylivuotolistaa ei ole, next=null.
     */
    private DictionaryEntry next;

    /**
     * Konstruktori luo uuden entryn, joka vastaa haluttua käännöstä.
     * Ylivuotolista on alussa tyhjä.
     *
     * @param codeword Lisättävän käännöksen koodisana (hajautustaulun avain)
     * @param bitstring Lisättävän koodisanan käännös (hajautustaulun arvo)
     */
    public DictionaryEntry(String codeword, String bitstring) {
        this.bitString = bitstring;
        this.codeword = codeword;
    }
    /**
     * Palauttaa alkiota vastaavan käännöksen (lähdetekstin osa).
     * @return käännös.
     */
    public String getBitstring() {
        return bitString;
    }
    /**
     * Lisää tätä alkiota seuraavan alkion ylivuotolistaan.
     * @param next Ylivuotolistan seuraava alkio.
     */
    public void setNext(DictionaryEntry next) {
        this.next = next;
    }
    /**
     * Palauttaa ylivuotolistan seuraavan alkion.
     * @return  ylivuotolistan seuraava alkio.
     */
    public DictionaryEntry getNext() {
        return next;
    }
    /**
     * Palauttaa alkioon liittyvän koodisanan.
     * @return koodisana.
     */
    public String getCodeword() {
        return codeword;
    }

}
