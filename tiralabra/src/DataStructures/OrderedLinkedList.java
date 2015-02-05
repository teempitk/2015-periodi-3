package DataStructures;

import Huffman.HuffmanNode;

/**
 * OrderedList erityisesti Huffman-puun rakennusta varten kehitetty listatyyppi.
 * Listaan tallennetaan HuffmanNodeja, ja se on aina järjestyksessä. Listan
 * rakentaminen ja uuden alkion lisääminen vaatii siten hieman aikaa, mutta
 * toisaalta pienin alkio saadaan listasta vakioajassa, eikä sitä tarvitse
 * koskaan järjestää. Käyttötarkoituksensa vuoksi listaan ei voi lisätä
 * HuffmanNodea, jonka frequency olisi 0.
 *
 * @author teemupitkanen1
 */
public class OrderedLinkedList {

    /**
     * Linkki listan ensimmäiseen solmuun, jonka kautta muihin päästään käsiksi.
     */
    private ListNode first;
    /**
     * Listassa olevien HuffmanNodejen lukumäärä
     */
    private int size;

    /**
     * Konstruktori alustaa paikallliset muuttujat.
     */
    public OrderedLinkedList() {
        first = null;
        size = 0;
    }

    /**
     * insert-metodi lisää solmun listaan. Lisääminen tapahtuu suoraan oikeaan
     * kohtaan nousevaan suuruusjärjestykseen, ja operaatio onkin
     * aikavaativuudeltaan O(n). Insert-metodilla ei voi lisätä listaan solmua,
     * jolla on negatiivinen esiintymismäärä.
     *
     * @param huffNode Listaan lisättävä solmu
     */
    public void insert(HuffmanNode huffNode) {
        if (huffNode.getFrequency() <= 0) {
            return;
        }
        size++;
        ListNode node = new ListNode(huffNode);
        if (first == null) {
            first = node;
            return;
        }       
        if(first.compareTo(node)>0){
            node.setNext(first);
            first=node;
            return;
        }
        ListNode spot = first;
        while (spot.getNext() != null && spot.getNext().compareTo(node) < 0) {
            spot = spot.getNext();
        }
        ListNode next = spot.getNext();
        spot.setNext(node);
        node.setNext(next);
    }

    /**
     * Palauttaa listan ensimmäisen, eli pienimmmän alkion. Jos lista on tyhjä,
     * palauttaa null.
     *
     * @return
     */
    public HuffmanNode removeSmallest() {
        if (first == null) {
            return null;
        }
        ListNode toreturn = first;
        first = first.getNext();
        size--;
        return toreturn.getHuffmanNode();
    }

    /**
     * Palauttaa listan koon.
     *
     * @return listan koko
     */
    public int size() {
        return size;
    }

}
