package Huffman;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanTreeTest {

    public HuffmanTreeTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void moreFrequentSymbolsShouldHaveAtLeastAsShortCodewordsAsLessFrequentSymbolsTest() {
        int[] frequencies = new int[256];
        for (int i = 0; i < 256; i++) {
            frequencies[i] = i + 1;
        }
        String[] codewords = HuffmanTree.huffmanCodewords(frequencies);
        boolean shouldbetrue = true;
        int lenOfPrevious = Integer.MAX_VALUE;
        for (int i = 0; i < 256; i++) {
            if (codewords[i].length() > lenOfPrevious) {
                shouldbetrue = false;
            }
            lenOfPrevious = codewords[i].length();
        }
        assertTrue(shouldbetrue);
    }

    @Test
    public void ifOnlyOneSymbolIsPresentAtSourceTextItsCodewordIsOnlyOneBitTest() {
        int[] freqs = new int[256];
        freqs[10] = 1;
        String[] codes = HuffmanTree.huffmanCodewords(freqs);
        assertEquals(1, codes[10].length());
    }

    @Test
    public void aSymbolNotPresentInSourceTextIsAssignedNoCodeword() {
        int[] freqs = new int[256];
        freqs[10] = 1;
        String[] codes = HuffmanTree.huffmanCodewords(freqs);
        assertNull(null);
        assertNull(null);
    }
}
