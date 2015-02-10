package Huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tämä testi testaa sekä Huffman-koodausta että -purkua, koska operaatioiden
 * itsenäinen testaus on varsin kömpelöä. Pari ensimmäistä testiä testaa
 * lyhyempiä merkkijonoja, lopussa on automatisoitu testaus Alice's adventrures
 * in wonderlandin ja Turingin kuvan pakkaukseen.
 *
 * @author teemupitkanen1
 */
public class HuffmanTest {

    public HuffmanTest() {
    }

    private File file;
    private PrintWriter writer;

    @Before
    public void setUp() throws FileNotFoundException {
        file = new File("huffmanCompressionDecompressionTestFile1");
        writer = new PrintWriter(file);
    }

    @After
    public void tearDown() {
        File file2 = new File("huffmanCompressionDecompressionTestFile2");
        File file3 = new File("huffmanCompressionDecompressionTestFile3");
        file.delete();
        file2.delete();
        file3.delete();
    }

    @Test
    public void huffmanCompressionAndDecompressionMatchTest() throws IOException {
        writer.write("aaa");
        writer.close();
        HuffmanEncoding.encode("huffmanCompressionDecompressionTestFile1", "huffmanCompressionDecompressionTestFile2");
        HuffmanDecoding.decode("huffmanCompressionDecompressionTestFile2", "huffmanCompressionDecompressionTestFile3");
        Scanner scan = new Scanner(new File("huffmanCompressionDecompressionTestFile3"));
        assertEquals("aaa", scan.nextLine());
        assertFalse(scan.hasNext());
    }

    @Test
    public void huffmanCompressionAndDecompressionMatchForTextWithMultipleLinesTest() throws IOException {
        writer.write("aaa\nbbb\nccc");
        writer.close();
        HuffmanEncoding.encode("huffmanCompressionDecompressionTestFile1", "huffmanCompressionDecompressionTestFile2");
        HuffmanDecoding.decode("huffmanCompressionDecompressionTestFile2", "huffmanCompressionDecompressionTestFile3");
        Scanner scan = new Scanner(new File("huffmanCompressionDecompressionTestFile3"));
        assertEquals("aaa", scan.nextLine());
        assertEquals("bbb", scan.nextLine());
        assertEquals("ccc", scan.nextLine());
        assertFalse(scan.hasNext());
    }

    @Test
    public void huffmanCompressionAndDecompressionMatchForTextWithVariousCharactersTest() throws IOException {
        writer.write("äöäöääöäöä'#€€%&&(/=)?=)(&%&€%&#abfhesirjfzx<<>>>");
        writer.close();
        HuffmanEncoding.encode("huffmanCompressionDecompressionTestFile1", "huffmanCompressionDecompressionTestFile2");
        HuffmanDecoding.decode("huffmanCompressionDecompressionTestFile2", "huffmanCompressionDecompressionTestFile3");
        Scanner scan = new Scanner(new File("huffmanCompressionDecompressionTestFile3"));
        assertEquals("äöäöääöäöä'#€€%&&(/=)?=)(&%&€%&#abfhesirjfzx<<>>>", scan.nextLine());
    }

    @Test
    public void huffmanCompressionOfAnImageWorksTest() throws IOException, NoSuchAlgorithmException {
        HuffmanEncoding.encode("sampleFiles/turing.jpg", "huffmanCompressionDecompressionTestFile2");
        HuffmanDecoding.decode("huffmanCompressionDecompressionTestFile2", "huffmanCompressionDecompressionTestFile3");
        Path pathToOriginalData = Paths.get("sampleFiles/turing.jpg");
        byte[] originalData = Files.readAllBytes(pathToOriginalData);
        Path pathToDecompressedData = Paths.get("huffmanCompressionDecompressionTestFile3");
        byte[] decompressedData = Files.readAllBytes(pathToDecompressedData);
        assertArrayEquals(originalData, decompressedData);
    }

    @Test
    public void huffmanCompressionOfAliceWorksTest() throws IOException, NoSuchAlgorithmException {
        HuffmanEncoding.encode("sampleFiles/alice.txt", "huffmanCompressionDecompressionTestFile2");
        HuffmanDecoding.decode("huffmanCompressionDecompressionTestFile2", "huffmanCompressionDecompressionTestFile3");
        Path pathToOriginalData = Paths.get("sampleFiles/alice.txt");
        byte[] originalData = Files.readAllBytes(pathToOriginalData);
        Path pathToDecompressedData = Paths.get("huffmanCompressionDecompressionTestFile3");
        byte[] decompressedData = Files.readAllBytes(pathToDecompressedData);
        assertArrayEquals(originalData, decompressedData);
    }
}
