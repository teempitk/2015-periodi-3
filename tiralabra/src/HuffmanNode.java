
/**
 * HuffmanNode on Huffman-puun sisäsolmu tai lehti. HuffmanNode on 'Symbol'in
 * aliluokka, ja jokaisella solmulla on siis myös symbolin ominaisuudet.
 * Symbolimerkillä on väliä vain lehtisolmuissa, mutta alipuiden yhteenlasketut
 * frekvenssit täytyy ylläpitää oikein myös sisäsolmuille.
 *
 * @author teemupitkanen1
 */
public class HuffmanNode extends Symbol {

    /**
     * Solmun vasen lapsi.
     */
    private final HuffmanNode leftChild;
    /**
     * Solmun oikea lapsi.
     */
    private final HuffmanNode rightChild;

    /**
     * Konstruktori luo uuden solmun, ja yksinkertaisesti asettaa solmun
     * attribuuteille parametreina annetut arvot.
     *
     * @param s Solmua vastaava symboli (merkitystä vain lehtisolmuille)
     * @param freq Solmua vastaava symbolin esiintymismäärä tekstissä.
     * Lehtisolmuille solmun symbolin frekvenssi, sisäsolmuille vastaavan
     * alipuun lehtien symbolien yhteenlaskettu frekvenssi.
     * @param left solmun vasen lapsi.
     * @param right solmun oikea lapsi.
     */
    public HuffmanNode(char s, int freq, HuffmanNode left, HuffmanNode right) {
        super(s, freq);
        this.leftChild = left;
        this.rightChild = right;
    }

    /**
     * Palauttaa solmun vasemman lapsen.
     *
     * @return
     */
    public HuffmanNode getLeft() {
        return leftChild;
    }

    /**
     * Palauttaa solmun oikean lapsen.
     *
     * @return
     */
    public HuffmanNode getRight() {
        return rightChild;
    }

}
