
package Huffman;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author teemupitkanen1
 */
public class HuffmanEncoding {
    
    private static int nonascii;
    private static int[] freqs;
    
    public static void encode(String inFile, String outFile){
        
        nonascii = 0;
        freqs = new int[256];
        for (int i = 0; i < 256; i++) {
            freqs[i] = 0;
        }
        if(!inFile.substring(inFile.length()-4).equals(".txt")){
            System.out.println("At the moment, only .txt -files can be compressed");
            System.exit(0);
        }
        Scanner scan = null;
        try {
            scan = new Scanner(new File(inFile));
        } catch (Exception e) {
            System.out.println("No such file");
            System.exit(0);
        }
        while (scan.hasNext()) {
            countSymbolsInOneLine(scan.nextLine());
        }
        if (nonascii > 0) {
            System.out.println("Text contains " + nonascii + " characters not"
                    + " included in 8-bit ASCII. Those are lost in the compression.");
        }
        scan.close();
        String[] codes = HuffmanTree.huffmanCodewords(freqs);
        for (int i = 0; i < 256; i++) {
            System.out.println(i + ":" + codes[i]);
        }
    }

    private static void countSymbolsInOneLine(String line) {
        for (int i = 0; i < line.length(); i++) {
            char index = line.charAt(i);
            if (index < 256) {
                freqs[index]++;
            } else {
                nonascii++;
                System.out.println(line);
            }
        }
        freqs[10]++; // Rivi päättyy aina rivinvaihtoon.
    }
}
