package DataStructures;

import Huffman.HuffmanNode;

/**
 * ListNode on OrderedLinkedList-listan alkio. Alkio sisältää tiedon itseään
 * vastaavasta HuffmanNodesta, ja itseään listassa seuraavasta ListNodesta
 * (yksisuuntainen linkitys).
 *
 * @author teemupitkanen1
 */
public class ListNode implements Comparable<ListNode> {

    /**
     * Solmuun liittyvä HuffmanNode
     */
    private final HuffmanNode huffNode;
    /**
     * Listassa seuraava solmu
     */
    private ListNode next;

    /**
     * Konstruktori alustaa listassa seuraavan solmun tyhjäksi, ja asettaa
     * solmuun liittyvän HuffmanNoden.
     *
     * @param node Solmuun liittyvä HuffmanNode.
     */
    public ListNode(HuffmanNode node) {
        next = null;
        huffNode = node;
    }

    /**
     * Palauttaa listassa tätä seuraavan alkion.
     *
     * @return seuraava alkio.
     */
    public ListNode getNext() {
        return next;
    }

    /**
     * Asettaa parametrina saadun alkion listassa tätä seuraavaksi.
     *
     * @param next seuraava alkio.
     */
    public void setNext(ListNode next) {
        this.next = next;
    }

    /**
     * Palauttaa solmua vastaavan HuffmanNoden.
     *
     * @return solmua vastaava HuffmanNode.
     */
    public HuffmanNode getHuffmanNode() {
        return huffNode;
    }

    /**
     * Vertailee solmuja niitä vastaavien HuffmanNodejen perusteella.
     *
     * @param o verrattava ListNode
     * @return Negatiivinen kokonaisluku, jos tämä ListNode on "pienempi" kuin parametrina
     * annettu. 0 jos sama arvo. Muuten positiivinen arvo.
     */
    @Override
    public int compareTo(ListNode o) {
        return huffNode.compareTo(o.getHuffmanNode());
    }
}
