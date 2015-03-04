package DataStructures;

import java.util.Comparator;
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
public class MyLinkedListTest {

    private Comparator<Integer> comp = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    };
    
    private MyLinkedList list;

    public MyLinkedListTest() {
    }

    @Before
    public void setUp() {
        list = new MyLinkedList();
    }

    @Test
    public void addingEntriesToStartIncreasesListSizeTest(){
        list.addLast(3);
        assertEquals(1,list.size());
    }
    @Test
    public void addingEntriesToEndIncreasesListSizeTest(){
        list.addLast(3);
        assertEquals(1,list.size());
    }
    @Test
    public void addingMultipleEntriesIncreasesSizeCorrectlyTest(){
        list.addLast(3);
        list.addLast(1);
        list.addLast(2);
        list.addLast(6);
        list.addLast(2);
        list.addLast(2);
        assertEquals(6,list.size());
    }
    @Test
    public void getFirstReturnsCorrectlyFromSizeOneListTest(){
        list.addFirst(2);
        assertEquals(2,list.getFirst());
    }
    @Test
    public void getFirstReturnsCorrectlyFromBiggerListTest(){
        list.addLast(3);
        list.addFirst(1);
        list.addLast(2);
        list.addFirst(6);
        list.addLast(2);
        list.addLast(2);
        assertEquals(6,list.getFirst());
    }
    @Test
    public void removingEntriesDecreasesSizeTest(){
        list.addFirst(2);
        list.addLast(3);
        list.removeFirst();
        assertEquals(1,list.size());
    }
    @Test
    public void removingFirstRemovesTheFirstElement(){
        list.addFirst(2);
        list.addLast(3);
        list.removeFirst();
        assertEquals(3,list.getFirst());
    }
    @Test
    public void addingToListEndKeepsCorrectOrderTest(){
        list.addFirst(2);
        list.addLast(3);
        list.removeFirst();
        assertEquals(3,list.getFirst());
    }
    @Test
    public void orderPreservingAdditionWorksTest(){
        list.addPreservingOrder(4,comp);
        list.addPreservingOrder(2,comp);
        list.addPreservingOrder(5,comp);
        list.addPreservingOrder(1,comp);
        list.addPreservingOrder(3,comp);
        int[] inorder = new int[5];
        int[] correct = {1, 2, 3, 4, 5};
        for (int i=0;i<5;i++){
            inorder[i]=(int)list.getAndRemoveFirst();
        }
        assertArrayEquals(correct,inorder);
    }
    @Test
    public void orderPreservingAdditionWorksTest2(){
        list.addPreservingOrder(1,comp);
        list.addPreservingOrder(1,comp);
        list.addPreservingOrder(1,comp);
        list.addPreservingOrder(1,comp);
        list.addPreservingOrder(1,comp);
        int[] inorder = new int[5];
        int[] correct = {1, 1, 1, 1, 1};
        for (int i=0;i<5;i++){
            inorder[i]=(int)list.getAndRemoveFirst();
        }
        assertArrayEquals(correct,inorder);
    }
    @Test
    public void orderPreservingAdditionWorksTest3(){
        list.addPreservingOrder(1,comp);
        list.addPreservingOrder(2,comp);
        list.addPreservingOrder(3,comp);
        list.addPreservingOrder(4,comp);
        list.addPreservingOrder(5,comp);
        int[] inorder = new int[5];
        int[] correct = {1, 2, 3, 4, 5};
        for (int i=0;i<5;i++){
            inorder[i]=(int)list.getAndRemoveFirst();
        }
        assertArrayEquals(correct,inorder);
    }
    @Test
    public void indedxOfFindsCorrectIndexTest(){
        list.addLast(4);
        list.addLast(3);
        list.addLast(1);
        list.addLast(5);
        list.addLast(2);
        assertEquals(2,list.indexOf(1));
    }
    @Test
    public void indedxOfFindsCorrectIndexTest2(){
        list.addLast(4);
        list.addLast(3);
        list.addLast(1);
        list.addLast(5);
        list.addLast(2);
        assertEquals(0,list.indexOf(4));
    }
    @Test
    public void indedxOfFindsCorrectIndexTest3(){
        list.addLast(4);
        list.addLast(3);
        list.addLast(1);
        list.addLast(5);
        list.addLast(2);
        assertEquals(4,list.indexOf(2));
    }
    @Test
    public void removingEntriesByIndexWorksTest(){
        list.addLast(4);
        list.addLast(3);
        list.addLast(1);
        list.addLast(5);
        list.addLast(2);
        list.removeAtIndex(0);
        int[] correct = {3,1,5,2};
        for(int i=0;i<4; i++){
            assertEquals(correct[i],list.getAndRemoveFirst());
        }
    }
    @Test
    public void removingEntriesByIndexWorksTest2(){
        list.addLast(4);
        list.addLast(3);
        list.addLast(1);
        list.addLast(5);
        list.addLast(2);
        list.removeAtIndex(3);
        int[] correct = {4,3,1,2};
        for(int i=0;i<4; i++){
            assertEquals(correct[i],list.getAndRemoveFirst());
        }
    }
    @Test
    public void gettingElementByIndexWorksTest(){
        list.addLast(4);
        list.addLast(3);
        list.addLast(1);
        list.addLast(5);
        list.addLast(2);
        assertEquals(5,list.getElementAtIndex(3));
    }
    @Test
    public void indexOfReturnsMinusOneForMissingElemsTest(){
        list.addLast(4);
        list.addLast(3);
        assertEquals(-1,list.indexOf(2));
    }
    @Test
    public void removingFromNonExistentIndexDoesNothingTest(){
        list.addLast(4);
        list.addLast(3);
        list.removeAtIndex(2);
        assertEquals(2,list.size());
        assertEquals(4,list.getAndRemoveFirst());
        assertEquals(3,list.getAndRemoveFirst());
    }

}
