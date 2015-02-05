
package DataStructures;

import Huffman.HuffmanNode;
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
public class ListNodeTest {
    
    private HuffmanNode huff1;
    private HuffmanNode huff2;
    private HuffmanNode huff3;
    private ListNode node1;
    private ListNode node2;
    private ListNode node3;
    
    public ListNodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        huff1 = new HuffmanNode('a',1);
        huff2 = new HuffmanNode('b',2);
        huff3 = new HuffmanNode('c',2);
        node1 = new ListNode(huff1);
        node2 = new ListNode(huff2);
        node3 = new ListNode(huff3);
        node2.setNext(node3);
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void comparingTwoEqualNodesReturnsZeroTest(){
        assertEquals(0,node2.compareTo(node3));
    }
    @Test
    public void comparingToABiggerNodeReturnsNegativeTest(){
        assertTrue(node1.compareTo(node2)<0);
    }
    @Test
    public void comparingToASmallerNodeReturnsPositiveTest(){
        assertTrue(node2.compareTo(node1)>0);
    }
    @Test
    public void constructorSetsNextNodeInListToNullTest(){
        assertNull(node1.getNext());
    }
    @Test
    public void linkToNextNodeCanBeSetAndGotTest(){
        assertEquals(node3, node2.getNext());
    }
    @Test
    public void getHuffmanNodeReturnsCorrectHuffmanNodeTest(){
        assertEquals(huff2,node2.getHuffmanNode());
    }
}
