/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Huffman;

import IO.BitWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
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
public class HuffmanDecodingTest {

    private File file;

    public HuffmanDecodingTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FileNotFoundException, IOException {
        file = new File("decodingtestinput");
        BitWriter writer = new BitWriter(file);
        for (int i = 0; i < 65; i++) {
            writer.writeBits("00000000");
        }
        writer.writeBits("00000001");//A
        writer.writeBits("0");
        writer.writeBits("00000010");//B
        writer.writeBits("10");
        writer.writeBits("00000011");//C
        writer.writeBits("110");
        for (int i = 68; i < 256; i++) {
            writer.writeBits("00000000");
        }
        writer.writeBits("00000011");//EOF
        writer.writeBits("111");
        writer.writeBits("0101101100111");//ABCCA(eof)/
        writer.writeTheLastBits("");
    }

    @After
    public void tearDown() {
        file.delete();
    }

    @Test
    public void decoderDecodesMessageCorrectly() throws IOException{
        HuffmanDecoding.decode("decodingtestinput", "decodingtestputput");
        Scanner scan = new Scanner(new File("decodingtestputput"));
        assertEquals("ABCCA",scan.nextLine());
    }
}
