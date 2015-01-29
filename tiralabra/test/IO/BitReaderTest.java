package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BitReaderTest {

    public BitReaderTest() {
    }

    private BitReader reader;
    private File file;

    @Before
    public void setUp() throws FileNotFoundException, IOException {
        file = new File("bitreadertestfile.txt");
        PrintWriter writer = new PrintWriter(file);
        writer.print("ABCDEFG");
        writer.close();
        reader = new BitReader(file);
    }
    @After()
    public void tearDown() throws IOException{
        reader.close();
        file.delete();
    }

    @Test
    public void readOneByteTest() throws IOException {
        assertEquals("01000001", reader.readBits(8));
    }

    @Test
    public void readLessThanOneByteTest() throws IOException {
        assertEquals("010", reader.readBits(3));
    }

    @Test
    public void readOneByteInTwoPartsTest() throws IOException {
        assertEquals("01000001", reader.readBits(5) + reader.readBits(3));
    }

    @Test
    public void hasNextReturnsTrueWhenTestingOnNewFileTest() throws IOException {
        assertTrue(reader.hasNext());
    }

    @Test
    public void hasNextReturnsTrueWhenOneBitRemainingTest() throws IOException {
        reader.readBits(55);
        assertTrue(reader.hasNext());
    }

    @Test
    public void hasNextReturnsTrueWhenOneByteRemainingTest() throws IOException {
        reader.readBits(48);
        assertTrue(reader.hasNext());
    }

    @Test
    public void hasNextReturnsFalseWhenAllBitsReadTest() throws IOException {
        reader.readBits(56);
        assertFalse(reader.hasNext());
    }

    @Test
    public void whenReadingMoreBitsThanIsLeftReaderReturnsWhatItHasTest() throws IOException {
        reader.readBits(54);
        assertEquals("11",reader.readBits(3));
    }

}
