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
public class DictionaryEntryTest {
    
    private DictionaryEntry entry1;
    private DictionaryEntry entry2;
    private DictionaryEntry entry3;
    
    public DictionaryEntryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        entry3=new DictionaryEntry("code3","byte3",null);
        entry2=new DictionaryEntry("code2","byte2",entry3);
        entry1=new DictionaryEntry("code1","byte1",entry2);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void constructorSetsCodewordAndBitstringCorrectlyTest(){
        assertEquals("code1",entry1.getCodeword());
        assertEquals("byte1",entry1.getBitstring());
    }
    
    @Test
    public void entryChainingWorksTest(){
        assertEquals(entry3, entry1.getNext().getNext());
    }
}
