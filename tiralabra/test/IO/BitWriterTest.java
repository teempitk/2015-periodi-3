package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BitWriterTest {

    public BitWriterTest() {
    }

    private File file;
    private BitWriter writer;
    
    @Before
    public void setUp() throws FileNotFoundException {
        file = new File("bitwritertestfile.txt");
        writer = new BitWriter(file);
    }
    
    @After
    public void tearDown(){
        file.delete();
    }

    @Test
    public void writingASCIICharactersAsBytesWorksTest() throws IOException {
        writer.writeBits("01001101");
        writer.writeBits("01001111");
        writer.writeBits("01001001");
        writer.writeBits("00100001");
        writer.writeTheLastBits("");
        Scanner scan = new Scanner(file);
        assertEquals("MOI!", scan.nextLine());
        scan.close();
    }

    @Test
    public void writingSeveralButesInOneStringWorksTest() throws IOException {
        writer.writeBits("01001101010011110100100100100001");
        writer.writeTheLastBits("");
        Scanner scan = new Scanner(file);
        assertEquals("MOI!", scan.nextLine());
        scan.close();
    }

    @Test
    public void writingUnevenBytesWorksTest() throws IOException {
        writer.writeBits("010011010100");
        writer.writeBits("111101001001");
        writer.writeBits("0010000");
        writer.writeBits("1");
        writer.writeTheLastBits("");
        Scanner scan = new Scanner(file);
        assertEquals("MOI!", scan.nextLine());
        scan.close();
    }

    @Test
    public void writeTheLastBitsCorrectlyFillsTheLastByteWithZeros() throws IOException {
        writer.writeBits("01");
        writer.writeTheLastBits("01");
        Scanner scan = new Scanner(file);
        assertEquals("P", scan.nextLine());
        scan.close();
    }
}
