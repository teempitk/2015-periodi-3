/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

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
public class CodewordDictionaryTest {
    
    private CodewordDictionary dict;
    
    public CodewordDictionaryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        dict = new CodewordDictionary();
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void aNewDictionaryHasSizeZero(){
        assertEquals(0,dict.size());
    }
    
    @Test
    public void afterAddingOneTranslationSizeIsOneTest(){
        dict.insertCodeword("01", "01010101");
        assertEquals(1,dict.size());
    }
    
    @Test
    public void containsReturnsTrueIfTheCodewordHasBeenAddedTest(){
        dict.insertCodeword("010", "01010101");
        assertTrue(dict.containsCodeword("010"));
    }
    
    @Test
    public void containsReturnsFalseIfThereIsNoSuchCodeword(){
        dict.insertCodeword("010","01010101");
        assertFalse(dict.containsCodeword("000"));
    }
    
    @Test
    public void emptyDictionaryDoesntContainCodewordTest(){
        assertFalse(dict.containsCodeword("0"));
    }
    
    @Test
    public void containsFindsCodewordIfMultipleTranslationsHaveBeenAdded(){
        dict.insertCodeword("000", "00000000");
        dict.insertCodeword("010", "01100000");
        dict.insertCodeword("111", "00000100");
        dict.insertCodeword("01010", "11110000");
        dict.insertCodeword("011110", "11111111");
        assertTrue(dict.containsCodeword("111"));
    }
    @Test
    public void getReturnsCorrectTranslation(){
        dict.insertCodeword("000", "00000000");
        dict.insertCodeword("010", "01100000");
        dict.insertCodeword("111", "00000100");
        dict.insertCodeword("01010", "11110000");
        dict.insertCodeword("011110", "11111111");
        assertEquals("00000100",dict.get("111"));
    }
    @Test
    public void getReturnsNullIfNoSuchCodeword(){
        dict.insertCodeword("0", "00000000");
        assertNull(dict.get("1"));
    }
}
