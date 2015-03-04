package Huffman;

/**
 * HuffmanNode on Huffman-puun sisäsolmu tai lehti. Jokaista lehtisolmu vastaa
 * jokin lähdedatassa esiintyvä tavu, ja solmuun tallennetaan myös esiintymien
 * lukumäärä. Sisäsolmujen tavulla ei ole merkitystä, mutta esiintymismmäärien
 * arvoksi lasketaan aina sen molempien lapsien yhteenlaskettu esiintymismäärä.
 *
 * @author teemupitkanen1
 */
public class HuffmanNode implements Comparable<HuffmanNode> {

    /**
     * Solmun vasen lapsi.
     */
    private final HuffmanNode leftChild;
    /**
     * Solmun oikea lapsi.
     */
    private final HuffmanNode rightChild;
    /**
     * Solmuun liittyvä tavu (Tai 256 jos EOF).
     */
    private final int b;
    /**
     * Solmua vastaava tavujen esiintymismäärä. Jos solmu ei ole Huffman-puun
     * lehti, tämä on kaikkien solmun alipuun lehtien yhteenlaskettu
     * esiintymismäärä.
     */
    private final int freq;

    /**
     * Konstruktori, jota käytetään Huffman-puun lehtisolmujen luontiin. Kutakin
     * lehteä vastaa tavu, jonka arvo ja lukumäärä lähdedatassa annetaan
     * konstruktorille parametrina.
     *
     * @param b Solmua vastaava tavu.
     * @param freq Solmua vastaava symbolin esiintymismäärä tekstissä.
     */
    public HuffmanNode(int b, int freq) {
        this.b = b;
        this.freq = freq;
        this.leftChild = null;
        this.rightChild = null;
    }

    /**
     * Tätä konstrtuktoria käytetään puun sisäsolmujen luontiin. Sisäsolmuilla
     * solmua vastaavalla tavulla ei ole merkitystä, mutta esiintymiskertojen
     * määräksi asetetaan yhteenlaskettu lasten esiintymiskertojen määrä.
     *
     * @param left Solmun vasen lapsi
     * @param right Solmun oikea lapsi
     */
    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.freq = left.getFrequency() + right.getFrequency();
        this.b = 0;
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

    /**
     * Palauttaa solmun alipuussa olevia lehtisolmuja vastaavien tavujen
     * yhtenlasketun esiintymismäärän koodattavassa datassa.
     *
     * @return Esiintymien yhteenlaskettu määrä.
     */
    public int getFrequency() {
        return freq;
    }

    /**
     * Puun rakentamisessa solmuja vertaillaan keskenään niiden alipuun
     * lehtisolmujen yhteenlaskettujen esiintymiskertojen määrän perusteella.
     *
     * @param huff Solmu, johon verrataan.
     * @return Vertailun arvo. Negatiivinen, jos tämän solmun arvo on pienempi
     * kuin parmetrina annetun, 0 jos sama arvo ja positiivinen, jos tämän arvo
     * on suurempi.
     */
    @Override
    public int compareTo(HuffmanNode huff) {
        return freq - huff.getFrequency();
    }

    /**
     * Palauttaa solmua vastaavan tavun arvon. Tässä projektissa solmut 0-255
     * vastaavaat suoraan vastaavaa tavua (esim solmu 1=00000001), ja viimeinen
     * 256. solmu vastaa tiedoston loppumerkkiä.
     *
     * @return
     */
    public int getByteValue() {
        return b;
    }

}
