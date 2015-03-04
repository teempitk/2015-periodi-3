package Huffman;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanNodeTest {

    public HuffmanNodeTest() {
    }

    private HuffmanNode leftChild;
    private HuffmanNode rightChild;
    private HuffmanNode parent;
    private HuffmanNode siblingOfParent;
    private HuffmanNode parentOfParent;

    @Before
    public void setUp() {
        leftChild = new HuffmanNode('a', 1);
        rightChild = new HuffmanNode('b', 1);
        parent = new HuffmanNode(leftChild, rightChild);
        siblingOfParent = new HuffmanNode('c', 1);
        parentOfParent = new HuffmanNode(parent, siblingOfParent);
    }

    @Test
    public void LeafNodeConstructorSetsCharCorrectlyTest() {
        assertEquals('a', leftChild.getByteValue());
    }

    @Test
    public void LeafNodeConstructorStoresFreqCorrectlyTest() {
        assertEquals(1, leftChild.getFrequency());
    }

    @Test
    public void LeftChildInitializedCorrectlyInInternalNodeTest() {
        assertEquals(leftChild, parent.getLeft());
    }

    @Test
    public void RightChildInitializedCorrectlyInInternalNodeTest() {
        assertEquals(rightChild, parent.getRight());
    }
    
    @Test
    public void FrequencyCalculatedCorrectlyForInternalNodeTest(){
        assertEquals(2, parent.getFrequency());
    }
    
    @Test
    public void FrequencyCalculatedCorrectlyForInternalNodeTest2(){
        assertEquals(3, parentOfParent.getFrequency());
    }
    
    @Test
    public void movingDownwardsInTheTreeGoesAsItShouldtest(){
        assertEquals(parentOfParent.getLeft().getRight(),rightChild);
    }
}
