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
     * @param next Ylivuotoketjussa seuraava alkio
     */
    public DictionaryEntry(String codeword, String bitstring, DictionaryEntry next) {
        this.bitString = bitstring;
        this.codeword = codeword;
        this.next=next;
    }
    /**
     * Palauttaa alkiota vastaavan käännöksen (lähdetekstin osa).
     * @return käännös.
     */
    public String getBitstring() {
        return bitString;
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
