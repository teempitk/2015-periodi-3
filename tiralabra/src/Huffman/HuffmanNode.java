package Huffman;

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
     * Konstruktori, jota käytetään Huffman-puun lehtisolmujen luontiin. Kutakin
     * lehteä vastaa symboli, jonka merkki (char) ja lukumäärä tekstissä
     * annetaan konstruktorille parametrina.
     * @param s Solmua vastaava symboli
     * @param freq Solmua vastaava symbolin esiintymismäärä tekstissä.
     */
    public HuffmanNode(char s, int freq) {
        super(s, freq);
        this.leftChild = null;
        this.rightChild = null;
    }
    /**
     * Tätä konstrtuktoria käytetään puun sisäsolmujen luontiin. Sisäsolmuilla
     * solmun symbolilla ei ole merkitystä, mutta esiintymiskertojen määräksi 
     * asetetaan yhteenlaskettu lasten esiintymiskertojen määrä.
     * @param left Solmun vasen lapsi
     * @param right Solmun oikea lapsi
     */
    public HuffmanNode(HuffmanNode left, HuffmanNode right){
        super(' ', left.getFrequency()+right.getFrequency());
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