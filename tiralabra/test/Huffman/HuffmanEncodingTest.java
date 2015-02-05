package Huffman;

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

/**
 *
 * @author teemupitkanen1
 */
public class HuffmanEncodingTest {

    private File filein;

    public HuffmanEncodingTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FileNotFoundException {
        filein = new File("testinputfile");
    }

    @After
    public void tearDown() {
        filein.delete();
        File fileout = new File("testoutputfile");
        fileout.delete();
    }

    /**
     * For this test, probably a short explanation is neccessary. A compressed
     * file always contains 257*1byte as the lengths of the 257 codewords. Those
     * are mostly "00000000":s, because only two codewords are needed: 1 for the
     * only char, and one for "EOF". With just two codewords, both of these
     * should have lenght 1 bit ("0" and "1"). Now, the length of the whole file
     * is 257 bytes + 2 bits(as the codewords) + 2 bits(the char once in source
     * text and eof once)+ filling with zeros(4) = 258 bytes.
     *
     * @throws IOException
     */
    @Test
    public void huffmanEncodingOfAOneCharTextFileShouldBe258BytesTest() throws IOException {
        PrintWriter writer = new PrintWriter(filein);
        writer.print("A");
        writer.close();
        HuffmanEncoding.encode("testinputfile", "testoutputfile");
        File fileout = new File("testoutputfile");
        assertEquals(258, fileout.length());

    }

    /**
     * As before, except now the size of encodings at the start of file + the
     * actual encoded data + eof = 258 bytes sharp, no filling with zeros
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void huffmanEncodingOfAFileContainingOneChar5TimesShouldStillBe258BytesTest() throws FileNotFoundException, IOException {
        PrintWriter writer = new PrintWriter(filein);
        writer.print("AAAAA");
        writer.close();
        HuffmanEncoding.encode("testinputfile", "testoutputfile");
        File fileout = new File("testoutputfile");
        assertEquals(258, fileout.length());
    }

    /**
     * As before, but now with 6 occurrences of the char, we should pass by the
     * 'even bytes' boundary by 1, get 7 zerobits to fill the last byte, and
     * have a total of 259 bytes.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void huffmanEncodingOfAFileContainingOneChar6TimesShouldBe259BytesTest() throws FileNotFoundException, IOException {
        PrintWriter writer = new PrintWriter(filein);
        writer.print("AAAAAA");
        writer.close();
        HuffmanEncoding.encode("testinputfile", "testoutputfile");
        File fileout = new File("testoutputfile");
        assertEquals(259, fileout.length());
    }

    /**
     * Now a file with 8 A's, 4 B's and 2 c's (+ 1 EOF). A is more common than
     * the rest of the chars combined, so it should have a codeword of length 1.
     * Similarly, B should have a codeword of length 2, C of length 3, and
     * finally, eof of length 4. The total size of the encodings should be
     * 257bytes+1bit+2bits+3bits+4bits = 258bytes2bits, and the actual data
     * should be 8*1bit + 4*2bits + 2*3bits + 1*4bits(eof)=26 bits=3bytes2bits.
     * So the filesize in total should be 258bytes2bits+3bytes2bits+zeros to
     * fill the last byte = 262 bytes.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void fileSizeMatchesForMoreComplicatedFileTest() throws FileNotFoundException, IOException {
        PrintWriter writer = new PrintWriter(filein);
        writer.print("AAAAAAAABBBBCC");
        writer.close();
        HuffmanEncoding.encode("testinputfile", "testoutputfile");
        File fileout = new File("testoutputfile");
        assertEquals(262, fileout.length());
    }

}
