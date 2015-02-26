/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Huffman.HuffmanNode;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author teemupitkanen1
 */
public class OrderedLinkedListTest {

    private DynamicList list;

    public OrderedLinkedListTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        list = new DynamicList();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void newListHasSizeZeroTest() {
        assertEquals(0, list.size());
    }

    @Test
    public void afterInsertingOneNodeListHasSizeOneTest() {
        list.insert(new HuffmanNode('a', 1));
        assertEquals(1, list.size());
    }

    @Test
    public void insertingNodeWithZeroFrequencyDoesntIncreaseSizeTest() {
        list.insert(new HuffmanNode('a', 0));
        assertEquals(0, list.size());
    }

    @Test
    public void removingSmallestFromListWithJustOneZeroFrequencyNodeAddedReturnsNullTest() {
        list.insert(new HuffmanNode('a', 0));
        assertNull(list.removeSmallest());
    }

    @Test
    public void removeSmallestForEmptyListReturnsNullTest() {
        assertNull(list.removeSmallest());
    }

    @Test
    public void removingNodeAfterAddingWorksTest() {
        HuffmanNode huff1 = new HuffmanNode('a', 1);
        list.insert(huff1);
        assertEquals(huff1, list.removeSmallest());
    }

    @Test
    public void ListEntriesAreReturnedInCorrectOrderTest() {
        Random rand = new Random();
        boolean shouldBeTrue = true;
        for (int i = 0; i < 100; i++) {
            list.insert(new HuffmanNode((char) i, rand.nextInt(10)));
        }
        int prev = 0;
        while (list.size() > 1) {
            int next = list.removeSmallest().getFrequency();
            if (next < prev) {
                shouldBeTrue = false;
            }
            prev = next;
        }
        assertTrue(shouldBeTrue);
    }
}
