package Huffman;

import IO.BitWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * HuffmanEncoding on täyden palvelun luokka Huffman-koodaukseen. Luokan ydin,
 * staattinen encode-metodi saa parametreinaan pakattavan tiedoston ja halutun
 * pakatun tiedoston nimen, ja suorittaa pakkauksen.
 *
 * @author teemupitkanen1
 */
public class HuffmanEncoding {

    /**
     * Muuttuja pitää kirjaa pakkauksessa hukkuvien merkkien (ei-8bit-ASCII)
     * määrästä.
     */
    private static int nonascii;
    /**
     * Taulukkoon kerätään tieto kunkin merkin esiintymiskerroista Huffman-
     * koodausta varten
     */
    private static int[] freqs;
    /**
     * Tiedoston lukuun käytettävä skanneri
     */
    private static Scanner scan;

    /**
     * codes tallettaa kunkin merkin bittikoodauksen merkkijonona, esim "01010".
     * Bittikoodaukset ovat taulukossa ASCII-merkkien numeroinnin mukaisesti.
     */
    private static String[] codes;
    
    /**
     * Bitwriter-olio hoitaa bittitason tiedostoon kirjoittamisen.
     */
    private static BitWriter bwriter;
    
    /**
     * Luokan ydinmetodi, jota kutsumalla tiedoston pakkaus tapahtuu.
     *
     * @param inFile Pakattava tiedosto
     * @param outFile Pakatun tiedoston nimi
     */
    public static void encode(String inFile, String outFile) throws IOException {
        nonascii = 0;
        freqs = new int[256];
        checkFileType(inFile);
        try {
            scan = new Scanner(new File(inFile));
        } catch (Exception e) {
            System.out.println("No such file");
            System.exit(0);
        }
        countSymbols();
        scan.close();
        if (nonascii > 0) {
            System.out.println("Text contains " + nonascii + " characters not"
                    + " included in 8-bit ASCII. Those are lost in the compression.");
        }
        codes = HuffmanTree.huffmanCodewords(freqs);
        bwriter = new BitWriter(new File(outFile));
        writeToFileAsBits(inFile, outFile);

    }

    /**
     * Kun merkittäiset koodisanat on selvitetty, tämä metodi huolehtii koko
     * tekstin koodauksesta ja tallentamisesta tiedostoon.
     *
     * @param outFile Kohdetiedosto
     * @throws IOException
     */
    private static void writeToFileAsBits(String inFile, String outFile) throws IOException {
        scan = new Scanner(new File(inFile));
        while (scan.hasNext()) {
            String line = scan.nextLine();
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) < 256) {
                    bwriter.writeBits(codes[line.charAt(i)]);
                }
            }
        }
        bwriter.writeTheLastBits("");
    }

    /**
     * Metodi tarkistaa, onko pakattava tiedosto tekstitiedosto, ja keskeyttää
     * ohjelman suorituksen jos ei.
     *
     * @param inFile Pakattavan tiedoston nimi
     */
    private static void checkFileType(String inFile) {
        if (!inFile.substring(inFile.length() - 4).equals(".txt")) {
            System.out.println("At the moment, only .txt -files can be compressed");
            System.exit(0);
        }
    }

    /**
     * Laskee kunkin merkin esiintymismäärät pakattavassa tiedostossa.
     */
    private static void countSymbols() {
        while (scan.hasNext()) {
            countSymbolsInOneLine(scan.nextLine());
        }
    }

    /**
     * Laskee kunkin merkin esiintymismäärät yhdellä rivillä.
     *
     * @param line Rivi, joka lasketaan.
     */
    private static void countSymbolsInOneLine(String line) {
        for (int i = 0; i < line.length(); i++) {
            char index = line.charAt(i);
            if (index < 256) {
                freqs[index]++;
            } else {
                nonascii++;
            }
        }
        freqs[10]++; // Rivi päättyy aina rivinvaihtoon.
    }
}
