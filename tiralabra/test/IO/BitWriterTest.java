
package IO;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class BitWriterTest {
    
    public BitWriterTest() {
    }
    

    @Before
    public void setUp() {
    }
    

    @Test
    public void writingASCIICharactersAsBytesWorksTest() throws IOException{
        File file = new File("test.txt");
        BitWriter writer = new BitWriter(file);
        writer.writeBits("01001101");
        writer.writeBits("01001111");
        writer.writeBits("01001001");
        writer.writeBits("00100001");
        writer.writeTheLastBits("");
        Scanner scan = new Scanner(new File("test.txt"));
        assertEquals("MOI!",scan.nextLine());
        scan.close();
    }
    
    @Test
    public void writingSeveralButesInOneStringWorksTest() throws IOException{
        File file = new File("test.txt");
        BitWriter writer = new BitWriter(file);
        writer.writeBits("01001101010011110100100100100001");
        writer.writeTheLastBits("");
        Scanner scan = new Scanner(new File("test.txt"));
        assertEquals("MOI!",scan.nextLine());
        scan.close();
    }
    
    @Test 
        public void writingUnevenBytesWorksTest() throws IOException{
        File file = new File("test.txt");
        BitWriter writer = new BitWriter(file);
        writer.writeBits("010011010100");
        writer.writeBits("111101001001");
        writer.writeBits("0010000");
        writer.writeBits("1");
        writer.writeTheLastBits("");
        Scanner scan = new Scanner(new File("test.txt"));
        assertEquals("MOI!",scan.nextLine());
        scan.close();
    }
        
        @Test
        public void writeTheLastBitsCorrectlyFillsTheLastByteWithZeros() throws IOException{
        File file = new File("test.txt");
        BitWriter writer = new BitWriter(file);
        writer.writeBits("01");
        writer.writeTheLastBits("");
        Scanner scan = new Scanner(new File("test.txt"));
        assertEquals("P",scan.nextLine());
        scan.close();
        }
}
