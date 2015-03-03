/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    };
    
    private MyLinkedList list;

    public MyLinkedListTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        list = new MyLinkedList();
    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void addingEntriesToStartIncreasesListSizeTest(){
        list.addFirst(2);
        assertEquals(1,list.size());
    }
    @Test
    public void addingEntriesToEndIncreasesListSizeTest(){
        list.addFirst(2);
        list.addLast(3);
        assertEquals(2,list.size());
    }
    @Test
    public void removingEntriesDecreasesSizeTest(){
        list.addFirst(2);
        list.addLast(3);
        list.removeFirst();
        assertEquals(1,list.size());
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
}
