package Huffman;

import java.io.File;
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
     * Luokan ydinmetodi, jota kutsumalla tiedoston pakkaus tapahtuu.
     *
     * @param inFile Pakattava tiedosto
     * @param outFile Pakatun tiedoston nimi
     */
    public static void encode(String inFile, String outFile) {
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
        String[] codes = HuffmanTree.huffmanCodewords(freqs);
        for (int i = 0; i < 256; i++) {
            System.out.println(i + ":" + codes[i]);
        }
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
     * @param line Rivi, joka lasketaan.
     */
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
