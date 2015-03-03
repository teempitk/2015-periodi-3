package DataStructures;

import java.util.Comparator;

/**
 * Linkitetty lista, joka toteuttaa projektin vaatimat operaatiot. Erityistä
 * toteutukselle on Huffman-puun rakentamiseen käytettävät järjestettyä listaa
 * ylläpitävät metodit. Listalle on kolme käyttötarkoitusta: 1. Huffman-puun
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

    public void addFirst(E val) {
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

    public void addLast(E val) {
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

    public E getFirst() {
        return (E) first.val;
    }

    public E getElementAtIndex(int index) {
        ListEntry entry = entryAtIndex(index);
        if (entry == null) {
            return null;
        }
        return (E) entry.val;
    }

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

    public E getAndRemoveFirst() {
        E val = getFirst();
        removeFirst();
        return val;
    }

    public int indexOf(E val) {
        if (size == 0) {
            return -1;
        }
        ListEntry curr = first;
        int index = 0;
        while (curr.next != null && val != curr.val) {
            curr = curr.next;
            index++;
        }
        if (val == curr.val) {
            return index;
        } else {
            return -1;
        }

    }

    public void removeFirst() {
        if (size > 0) {
            first = first.next;
        }
        size--;
    }

    public void removeAtIndex(int index) {
        ListEntry entry = entryAtIndex(index);
        if (entry != null) {
            if (entry.prev != null) {
                entry.prev.next = entry.next;
            }
            if (entry.next != null) {
                entry.next.prev = entry.prev;
            }
            size--;
        }
    }

    public void addPreservingOrder(E val, Comparator<E> comp) {
        size++;
        ListEntry entry = new ListEntry(val);
        if (first == null) {
            first = entry;
            last = entry;
            return;
        }
        if (comp.compare((E) first.val, val) > 0) {
            entry.next = first;
            first = entry;
            return;
        }
        ListEntry curr = first;
        while (curr.next != null && comp.compare((E) curr.next.val, val) < 0) {
            curr = curr.next;
        }
        entry.next = curr.next;
        entry.prev = curr;
        if(curr.next!=null){
            curr.next.prev=entry;
        }
        curr.next = entry;
    }
    
    public int size() {
        return size;
    }

    private class ListEntry<E> {

        private final E val;
        private ListEntry prev;
        private ListEntry next;

        public ListEntry(E val) {
            this.val = val;
        }
    }

}
