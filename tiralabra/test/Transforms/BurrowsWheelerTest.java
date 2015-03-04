
package Transforms;

import IO.BitReader;
import Tranforms.BurrowsWheeler;
import Utils.BitConversions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author teemupitkanen1
 */
public class BurrowsWheelerTest {

    public BurrowsWheelerTest() {
    }
    private File file;

    @Before
    public void setUp() throws FileNotFoundException {
        file = new File("BWTTestFile1");
    }

    @After
    public void tearDown() {
        File file2 = new File("BWTTestFile2");
        File file3 = new File("BWTTestFile3");
        file.delete();
        file2.delete();
        file3.delete();
    }

    @Test
    public void burrowsWheelerWritesCorrectPointerToStartOfFileTest() throws IOException{
        PrintWriter w1 = new PrintWriter("BWTTestFile2");
        w1.write("banana");
        w1.close();
        BurrowsWheeler.transform("BWTTestFile2", "BWTTestFile3");
        BitReader reader = new BitReader(new File("BWTTestFile3"));
        assertEquals("00000000000000000000000000000011",reader.readBits(32)); 
    }
    @Test
    public void burrowsWheelerTransformsBananaCorrectly() throws IOException{
        PrintWriter w1 = new PrintWriter("BWTTestFile2");
        w1.write("banana");
        w1.close();
        BurrowsWheeler.transform("BWTTestFile2", "BWTTestFile3");
        BitReader reader = new BitReader(new File("BWTTestFile3"));
        reader.readBits(32);
        String result = "";
        for(int i=0;i<6; i++){
            result+=(char)BitConversions.asByte(reader.readBits(8));
        }
        reader.close();
        assertEquals("nnbaaa",result); 
    }
    @Test
    public void burrowsWheelerInverseTransformsBananaCorrectly() throws FileNotFoundException, IOException{
        byte[] arr = {0,0,0,BitConversions.asByte("00000011"),'n','n','b','a','a','a'};
        FileOutputStream stream = new FileOutputStream("BWTTestFile2");
        stream.write(arr);
        stream.close();
        BurrowsWheeler.inverseTransform("BWTTestFile2", "BWTTestFile3");
        Scanner scan = new Scanner(new File("BWTTestFile3"));
        assertEquals("banana",scan.nextLine());
        scan.close();
    }
    @Test
    public void burrowsWheelerOnAnImageWorksTest() throws IOException, NoSuchAlgorithmException {
        BurrowsWheeler.transform("sampleFiles/turing.jpg", "BWTTestFile2");
        BurrowsWheeler.inverseTransform("BWTTestFile2", "BWTTestFile3");
        Path pathToOriginalData = Paths.get("sampleFiles/turing.jpg");
        byte[] originalData = Files.readAllBytes(pathToOriginalData);
        Path pathToDecompressedData = Paths.get("BWTTestFile3");
        byte[] decompressedData = Files.readAllBytes(pathToDecompressedData);
        assertArrayEquals(originalData, decompressedData);
    }

    @Test
    public void burrowsWheelerOnAliceWorksTest() throws IOException, NoSuchAlgorithmException {
        BurrowsWheeler.transform("sampleFiles/alice.txt", "BWTTestFile2");
        BurrowsWheeler.inverseTransform("BWTTestFile2", "BWTTestFile3");
        Path pathToOriginalData = Paths.get("sampleFiles/alice.txt");
        byte[] originalData = Files.readAllBytes(pathToOriginalData);
        Path pathToDecompressedData = Paths.get("BWTTestFile3");
        byte[] decompressedData = Files.readAllBytes(pathToDecompressedData);
        assertArrayEquals(originalData, decompressedData);
    }
}
