
package Utils;

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
public class BitConversionsTest {
    
    public BitConversionsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void convertingZeroTOStringPresentationOfAByteWorks(){
        int num = 0;
        assertEquals("00000000",BitConversions.integerAsByteString(num));
    }
    @Test
    public void convertingASmallIntegerToStringPresentationOfAByteTest(){
        int num = 59;
        assertEquals("00111011",BitConversions.integerAsByteString(num));
    }
    @Test
    public void convertingABiggerIntegerToStringPresentationOfAByteTest(){
        int num = 240;
        assertEquals("11110000",BitConversions.integerAsByteString(num));
    }
    @Test
    public void convertingTheBiggestPossibleIntegerThatFitsInOneUnsignedByteTest(){
        int num = 255;
        assertEquals("11111111",BitConversions.integerAsByteString(num));
    }
    @Test
    public void convertingBitString00000001ToByteTest(){
        String bits = "00000001";
        assertEquals(1,BitConversions.asByte(bits));
    }
    @Test
    public void convertingBitString00000000ToByteTest(){
        String bits = "00000000";
        assertEquals(0,BitConversions.asByte(bits));
    }
    @Test
    public void convertingBitString11111111ToByteTest(){
        String bits = "11111111";
        assertEquals(-1,BitConversions.asByte(bits));
    }
    @Test
    public void convertingBitString10000000ToByteTest(){
        String bits = "10000000";
        assertEquals(-128,BitConversions.asByte(bits));
    }
    @Test
    public void convertingBitString01010101ToByteTest(){
        String bits = "01010101";
        assertEquals(85,BitConversions.asByte(bits));
    }
    @Test
    public void intToByteArrayWorksTest(){
        int number = 3;
        byte[] correct = {0,0,0,3};
        byte[] res = BitConversions.intToByteArray(number);
        assertArrayEquals(correct,res);
    }
    @Test
    public void byteArrayToIntWorks(){
        byte[] arr = {0,0,0,3};
        int res = BitConversions.byteArrayToInt(arr);
        assertEquals(3,res);
    }
    @Test
    public void byteArrayToIntConversionsWorkBackAndForthTest(){
        int[] testnumbers = {456,435,0,-6,32,52,3456,-457,64424332};
        for (int i=0; i<testnumbers.length; i++){
            assertEquals(testnumbers[i], BitConversions.byteArrayToInt(BitConversions.intToByteArray(testnumbers[i])));
        }
    }
    
}
