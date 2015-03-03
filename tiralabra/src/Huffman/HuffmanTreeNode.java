package Huffman;

import java.util.Comparator;

public class HuffmanTreeNode implements Comparable<HuffmanNode>{

    
    private final HuffmanTreeNode leftChild;
    private final HuffmanTreeNode rightChild;
    private int freq;
    private char s;


    public HuffmanTreeNode(char s, int freq) {
        this.leftChild = null;
        this.rightChild = null;
        this.freq = freq;
        this.s=s;
    }

    public HuffmanTreeNode(HuffmanTreeNode left, HuffmanTreeNode right) {
        this.leftChild = left;
        this.rightChild = right;
        this.freq = left.getFrequency() + right.getFrequency();
        this.s=' ';
    }

    
    public HuffmanTreeNode getLeft() {
        return leftChild;
    }

    public HuffmanTreeNode getRight() {
        return rightChild;
    }

    @Override
    public int compareTo(HuffmanNode huff) {
        return freq-huff.getFrequency();
    }

    public int getFrequency() {
        return freq;
    }

    public char getSymbol() {
        return s;
    }

    public static class hComparator implements Comparator<HuffmanNode>{

        @Override
        public int compare(HuffmanNode o1, HuffmanNode o2) {
            return(o1.compareTo(o2));
        }
        
    }
}
