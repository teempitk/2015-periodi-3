
package Utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author teemupitkanen1
 */
public class ArrayUtilsTest {

    public ArrayUtilsTest() {
    }

    @Test
    public void quicksortSortsArrayCorrectlyTest1() {
        byte[] bytearr = {8,2,4,9,5,3,1,6,7,0};
        byte[] correct = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayUtils.quickSort(bytearr, 0, 9);
        assertArrayEquals(correct, bytearr);
    }
    @Test
    public void quicksortSortsArrayCorrectlyTest2() {
        byte[] bytearr = {1,1,1,1,1};
        byte[] correct = {1,1,1,1,1};
        ArrayUtils.quickSort(bytearr, 0, 4);
        assertArrayEquals(correct, bytearr);
    }
    @Test
    public void quicksortSortsArrayCorrectlyTest3() {
        byte[] bytearr = {1,2,3,4,5};
        byte[] correct = {1,2,3,4,5};
        ArrayUtils.quickSort(bytearr, 0, 4);
        assertArrayEquals(correct, bytearr);
    }
    @Test
    public void quicksortSortsArrayCorrectlyTest4() {
        byte[] bytearr = {1};
        byte[] correct = {1};
        ArrayUtils.quickSort(bytearr, 0, 0);
        assertArrayEquals(correct, bytearr);
    }
    @Test
    public void quicksortSortsArrayCorrectlyTest5() {
        byte[] bytearr = {5,4,3,2,1};
        byte[] correct = {1,2,3,4,5};
        ArrayUtils.quickSort(bytearr, 0, 4);
        assertArrayEquals(correct, bytearr);
    }
    @Test
    public void containsFindsOccurrenceTest(){
        String[] test = {"a","b","c"};
        String s = "b";
        assertTrue(ArrayUtils.contains(test, s));
    }
    @Test
    public void containsReturnsFalseIfMissingTest(){
        String[] test = {"a","b","c"};
        String s = "D";
        assertFalse(ArrayUtils.contains(test, s));
    }
}
