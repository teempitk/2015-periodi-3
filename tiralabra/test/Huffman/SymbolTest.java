
package Huffman;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class SymbolTest {

    public SymbolTest() {
    }
    
    private Symbol symbol1;
    private Symbol symbol2;
    private Symbol symbol3;
    private Symbol symbol4;
    private Symbol symbol5;

    @Before
    public void setUp() {
        symbol1=new Symbol('a',123);
        symbol2=new Symbol('a',123);
        symbol3=new Symbol('a',124);
        symbol4=new Symbol('a',122);
        symbol5=new Symbol('a',0);
    }
    @Test
    public void constructorSetsCharacterCorrectly() {
        assertEquals('a', symbol1.getSymbol());
    }

    @Test
    public void constructorSetsCharacterFrequencyCorrectly() {
        assertEquals(123, symbol1.getFrequency());
    }

    @Test
    public void comparisonReturnsZeroIfEqual() {
        assertEquals(0, symbol1.compareTo(symbol2));
    }

    @Test
    public void comparisonReturnsPositiveIfHigherFrequency() {
        assertTrue(symbol1.compareTo(symbol4) > 0);
    }

    @Test
    public void comparisonReturnsNegativeIfLowerFrequency() {
        assertTrue(symbol4.compareTo(symbol3) < 0);
    }
}
