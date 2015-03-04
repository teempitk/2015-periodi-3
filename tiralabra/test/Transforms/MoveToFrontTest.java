/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transforms;

import Tranforms.MoveToFront;
import java.io.File;
import java.io.FileInputStream;
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
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author teemupitkanen1
 */
public class MoveToFrontTest {

    public MoveToFrontTest() {
    }
    private File file;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FileNotFoundException {
        file = new File("MTFTestFile1");
    }

    @After
    public void tearDown() {
        File file2 = new File("MTFTestFile2");
        File file3 = new File("MTFTestFile3");
        file.delete();
        file2.delete();
        file3.delete();
    }

    @Test
    public void moveToFrontTransformOnBananaWorksTest() throws FileNotFoundException, IOException {
        PrintWriter w1 = new PrintWriter("MTFTestFile2");
        w1.write("banana");
        w1.close();
        MoveToFront.transform("MTFTestFile2", "MTFTestFile3");
        FileInputStream stream = new FileInputStream(new File("MTFTestFile3"));
        int[] result = new int[6];
        int[] correct = {98, 98, 110, 1, 1, 1};
        for (int i = 0; i < 6; i++) {
            int tmp = stream.read();
            if (tmp < 0) {
                tmp += 256;
            }
            result[i] = tmp;
        }
        stream.close();
        assertArrayEquals(correct, result);
    }
    @Test
    public void moveToFrontReverseTransformWorksOnBananaTest() throws FileNotFoundException, IOException{
        byte[] orig = {98,98,110,1,1,1};
        FileOutputStream stream = new FileOutputStream(new File("MTFTestFile2"));
        stream.write(orig);
        stream.close();
        MoveToFront.reverseTransform("MTFTestFile2", "MTFTestFile3");
        Scanner scan = new Scanner(new File("MTFTestFile3"));
        assertEquals("banana",scan.nextLine());
        scan.close();
    }

    @Test
    public void moveToFrontOnAnImageWorksTest() throws IOException, NoSuchAlgorithmException {
        MoveToFront.transform("sampleFiles/turing.jpg", "MTFTestFile2");
        MoveToFront.reverseTransform("MTFTestFile2", "MTFTestFile3");
        Path pathToOriginalData = Paths.get("sampleFiles/turing.jpg");
        byte[] originalData = Files.readAllBytes(pathToOriginalData);
        Path pathToDecompressedData = Paths.get("MTFTestFile3");
        byte[] decompressedData = Files.readAllBytes(pathToDecompressedData);
        assertArrayEquals(originalData, decompressedData);
    }

    @Test
    public void moveToFrontOnAliceWorksTest() throws IOException, NoSuchAlgorithmException {
        MoveToFront.transform("sampleFiles/alice.txt", "MTFTestFile2");
        MoveToFront.reverseTransform("MTFTestFile2", "MTFTestFile3");
        Path pathToOriginalData = Paths.get("sampleFiles/alice.txt");
        byte[] originalData = Files.readAllBytes(pathToOriginalData);
        Path pathToDecompressedData = Paths.get("MTFTestFile3");
        byte[] decompressedData = Files.readAllBytes(pathToDecompressedData);
        assertArrayEquals(originalData, decompressedData);
    }
}
