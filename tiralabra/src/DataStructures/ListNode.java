
package DataStructures;

import Huffman.HuffmanNode;

/**
 *
 * @author teemupitkanen1
 */
public class ListNode implements Comparable<ListNode>{
    
    private final HuffmanNode huffNode;
    
    private ListNode next;
    
    
    public ListNode(HuffmanNode node){
        next=null;
        huffNode = node;
    }
    
    public ListNode getNext(){
        return next;
    }
    
    public void setNext(ListNode next){
        this.next = next;
    }
    
    public HuffmanNode getHuffmanNode(){
        return huffNode;
    }

    @Override
    public int compareTo(ListNode o) {
        return huffNode.compareTo(o.getHuffmanNode());
    }
}
