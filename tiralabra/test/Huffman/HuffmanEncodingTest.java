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
     * Tämä testi vaatinee selitystä: Pakattu tiedosto sisältää aina 257 tavua,
     * jotka sisältävät koodisanojen pituudet. Tämän tiedoston pakkaukseen
     * tarvitaan vain kahta koodisanaa, 1 ainoalle merkille ja 1 tiedoston
     * lopulle (eof). Koska koodisanoja on vain 2, molempien pituus on 1 bitti
     * ("0" ja "1"). Nyt koko koodauksen pituus on (koodisanojen koodaus) +
     * (datan koodaus) + (eof) = (257 tavua + 2 bittiä)+(1bitti)+(1 bitti) = 257
     * tavua, 4 bittiä, ja tiedoston pituus on tämä + 0-bittejä niin monta että
     * päästään tasatavuihin, siis 258 tavua.
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
     * Tässä koodisanojen pakkauksen, datan pakkauksen ja tiedoston loppumerkin
     * pituus on tasan 258, lopussa ei ole täyttöä nollabiteillä.
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
     * Tässä koko koodauksen pituus on 258 tavua ja 1 bitti, siis loppuun tulee
     * seitsemän täytebittiä.
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
     * Nyt tiedosto, jossa on 8 A:ta, 4 B:tä, 2 C:tä ja 1 EOF-merkki. A on
     * yleisempi kuin muut merkit yhteensä, joten sen koodisanan pituus pitäisi
     * olla 1. Samoin B:n koodisanan pituus pitäisi olla 2, C:n 3, ja EOF:n 4.
     * Koodausten yhteispituus on 257tavua + 1bitti + 2bittiä + 3bittiä +
     * 4bittiä = 258tavua2bittiä. Pakatun datan (+eof:n) koko pitäisi olla
     * 8*1bitti + 4*2bittiä + 2*3bittiä + 1*4bittiä = 3tavua2bittiä. Yhteensä
     * koodauksen pituus on siis 261tavua4bittiä, nollatäytön kanssa 262tavua.
     *
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
