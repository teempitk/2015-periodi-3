package DataStructures;

import java.util.Comparator;

/**
 * Linkitetty lista, joka toteuttaa projektin vaatimat operaatiot. Erityistä
 * toteutukselle on Huffman-puun rakentamiseen käytettävät järjestettyä listaa
 * ylläpitävät metodit (addPreservingOrder lähinnä). Listalle on kolme käyttötarkoitusta: 1. Huffman-puun
 * solmujen tallennus puun rakennusvaiheessa. 2. MTF-muunnoksessa "bytelistin"
 * tallennus. 3. BWT:n purkuvaiheessa.
 *
 * @author teemupitkanen1
 */
public class MyLinkedList< E> {

    /**
     * Listassa olevien alkioiden määrä.
     */
    private int size;
    /**
     * Viite listan ensimmäiseen alkioon.
     */
    private ListEntry first;
    /**
     * Viite listan viimeiseen alkioon.
     */
    private ListEntry last;

    public MyLinkedList() {
        size = 0;
        first = null;
        last = null;
    }

    /**
     * Metodi lisää parametrina annetun arvon listan ensimmäiseksi.
     *
     * @param val Listaan lisättävä arvo.
     */
    public void insertFirst(E val) {
        ListEntry entry = new ListEntry(val);
        entry.next = first;
        first = entry;
        if (size == 0) {
            last = entry;
        } else {
            entry.next.prev = entry;
        }
        size++;
    }

    /**
     * Metodi lisää parametrina annetun arvon listan loppuun.
     *
     * @param val Listaan lisättävä arvo.
     */
    public void insertLast(E val) {
        ListEntry entry = new ListEntry(val);
        entry.prev = last;
        last = entry;
        if (size == 0) {
            first = entry;
        } else {
            entry.prev.next = entry;
        }
        size++;
    }

    /**
     * Palauttaa listan ensimmäisenä olevan arvon, muttei poista sitä listasta.
     *
     * @return Listan ensimmäinen arvo.
     */
    public E getFirst() {
        return (E) first.val;
    }

    /**
     * Palauttaa listan annetussa indeksissä olevan arvon.
     *
     * @param index Indeksi, jossa oleva arvo halutaan. Jos indeksi ei kelpaa,
     * palauttaa null.
     * @return
     */
    public E getElementAtIndex(int index) {
        ListEntry entry = entryAtIndex(index);
        if (entry == null) {
            return null;
        }
        return (E) entry.val;
    }

    /**
     * Luokan sisäisessä käytössä oleva metodi, johon muutama julkinen metodi
     * pohjautuu. Palauttaa ListEntryn indeksin perusteella.
     *
     * @param index Halutun ListEntryn indeksi.
     * @return Indeksissä oleva ListEntry.
     */
    private ListEntry entryAtIndex(int index) {
        if (index >= 0 && index < size) {
            ListEntry curr = first;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            return curr;
        } else {
            return null;
        }
    }

    /**
     * Listan ensimmäisen alkion palauttava ja poistava metodi.
     *
     * @return Listan ensimmäinen alkio.
     */
    public E getAndRemoveFirst() {
        E val = getFirst();
        removeFirst();
        return val;
    }

    /**
     * Etsii listasta parametrina annetun alkion, ja palauttaa sen indeksin.
     *
     * @param val Arvo, jota listasta etsitään.
     * @return Parametrina annetun arvon indeksi, -1 jos arvoa ei löydy.
     */
    public int indexOf(E val) {
        if (size == 0) {
            return -1;
        }
        ListEntry curr = first;
        int index = 0;
        while (curr.next != null && curr.val != val) {
            curr = curr.next;
            index++;
        }
        if (val == curr.val) {
            return index;
        } else {
            return -1;
        }

    }

    /**
     * Metodi poistaa listan ensimmäisen arvon.
     */
    public void removeFirst() {
        if (size > 0) {
            first = first.next;
        }
        size--;
    }

    /**
     * Poistaa listasta alkion parametrina annetusta indeksistä.
     *
     * @param index Poistettavan arvon indeksi.
     */
    public void removeAtIndex(int index) { 
        ListEntry entry = entryAtIndex(index);
        if (entry != null) {
            if (entry.prev != null) {
                entry.prev.next = entry.next;
            } else {
                first = entry.next;
            }
            if (entry.next != null) {
                entry.next.prev = entry.prev;
            } else {
                last = entry.prev;
            }
            size--;
        }
    }

    /**
     * Lisää listaan alkion siten, että jos lista on ennen lisäystä kasvavassa
     * suuruusjärjestyksessä, järjestys säilyy. Metodia käytetään Huffman-puuta
     * rakentaessa.
     *
     * @param val Listaan lisättävä arvo
     * @param comp Lisättävien arvojen vertailuun kelpaava comparator
     */
    public void insertPreservingOrder(E val, Comparator<E> comp) {
        size++;
        ListEntry entry = new ListEntry(val);
        if (first == null) {
            first = entry;
            last = entry;
            return;
        }
        if (comp.compare((E) first.val, val) > 0) {
            entry.next = first;
            first.prev = entry;
            first = entry;
            return;
        }
        ListEntry curr = first;
        while (curr.next != null && comp.compare((E) curr.next.val, val) < 0) {
            curr = curr.next;
        }
        entry.next = curr.next;
        entry.prev = curr;
        if (curr.next != null) {
            curr.next.prev = entry;
        }
        curr.next = entry;
    }

    /**
     * Palauttaa listassa olevien alkioiden lukumäärän.
     *
     * @return alkioiden lukumäärä.
     */
    public int size() {
        return size;
    }

    /**
     * Listaan lisättäviä alkioita kuvaava privaatti luokka.
     *
     * @param <E> Lisättävien alkioiden tyyppi (sama kuin koko listalla).
     */
    private class ListEntry<E> {

        private final E val;
        private ListEntry prev;
        private ListEntry next;

        public ListEntry(E val) {
            this.val = val;
        }
    }

}
