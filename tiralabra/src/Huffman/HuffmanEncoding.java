package Huffman;

import IO.BitWriter;
import Utils.BitConversions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * HuffmanEncoding on täyden palvelun luokka Huffman-koodaukseen. Luokan ydin,
 * staattinen encode-metodi saa parametreinaan pakattavan tiedoston ja halutun
 * pakatun tiedoston nimen, ja suorittaa pakkauksen.
 *
 * @author teemupitkanen1
 */
public class HuffmanEncoding {

    /**
     * Taulukkoon kerätään tieto kunkin erilaisen tavun esiintymiskerroista
     * Huffman- koodausta varten
     */
    private static int[] freqs;
    /**
     * codes tallettaa kunkin lähdetiedoston tavun bittikoodauksen merkkijonona,
     * esim "01010". Bittikoodaukset ovat taulukossa järjestyksessä, ensin tavun
     * "00000000" koodaus, viimeisenä tavun "11111111" koodaus.
     */
    private static String[] codes;

    /**
     * Bitwriter-olio hoitaa bittitason tiedostoon kirjoittamisen.
     */
    private static BitWriter bwriter;
    /**
     * Pakattavan tiedoston lukemiseen käytettävä ByteReader
     */
    private static FileInputStream breader;

    /**
     * Luokan ydinmetodi, jota kutsumalla tiedoston pakkaus tapahtuu.
     *
     * @param inFile Pakattava tiedosto
     * @param outFile Pakatun tiedoston nimi
     * @throws java.io.IOException
     */
    public static void encode(String inFile, String outFile) throws IOException {
        freqs = new int[257];
        freqs[256] = 1;
        breader = new FileInputStream(new File(inFile));
        long startCountBytes = System.nanoTime();
        countDifferentBytesInFile();
        long finishCountBytes = System.nanoTime();
        System.out.println("Time to count bytes: "+(finishCountBytes -startCountBytes)*1.0e-9+" sec");
        breader.close();
        long startBuildTree = System.nanoTime();// POISTA!POISTA!POISTA!POISTA!POISTA!POISTA!POISTA!POISTA!
        codes = HuffmanTree.huffmanCodewords(freqs);
        long finishBuildTree = System.nanoTime();// POISTA!POISTA!POISTA!POISTA!POISTA!POISTA!POISTA!POISTA!
        System.out.println("Time to build HuffmanTree: "+(finishBuildTree -startBuildTree)*1.0e-9+" sec");// POISTA!POISTA!POISTA!POISTA!POISTA!POISTA!POISTA!POISTA!
        bwriter = new BitWriter(new File(outFile));
        long startWriteEncoding = System.nanoTime();
        writeEncodingToTheStartOfFile();
        long finishWriteEncoding = System.nanoTime();
        System.out.println("Time to write encodings: "+(finishWriteEncoding -startWriteEncoding)*1.0e-9+" sec");
        long startWriteData = System.nanoTime();
        writeToFileAsBits(inFile, outFile);
        bwriter.writeTheLastBits(codes[256]);
        long finishWriteData = System.nanoTime();
        System.out.println("Time to write data: "+(finishWriteData -startWriteData)*1.0e-9+" sec");
    }

    /**
     * Kun tavuittaiset koodisanat on selvitetty, tämä metodi huolehtii koko
     * tekstin koodauksesta ja tallentamisesta tiedostoon.
     *
     * @param outFile Kohdetiedosto
     * @throws IOException
     */
    private static void writeToFileAsBits(String inFile, String outFile) throws IOException {
        breader = new FileInputStream(new File(inFile));
        while (breader.available() > 0) {
            int byteRead = breader.read();
            if (byteRead < 0) {
                byteRead += 256;
            }
            bwriter.writeBits(codes[byteRead]);
        }
        breader.close();
    }

    /**
     * Laskee kunkin erilaisen tavun esiintymismäärät pakattavassa tiedostossa.
     */
    private static void countDifferentBytesInFile() throws IOException {
        while (breader.available() > 0) {
            freqs[breader.read()]++;
        }
    }

    /**
     * Metodi kijoittaa tavuittaiset koodaukset pakatun tiedoston alkuun.
     * Pakatun tiedoston rakenne on seuraava: 1. Tavun "00000000" koodauksen
     * pituus 8 bitissä 2. Tavun "00000000" koodaus 3. Tavun "00000001"
     * koodauksen pituus ... ... ... 512. Tavun "11111111" koodaus 513.
     * EOF-merkin koodauksen pituus 514. EOF-merkin koodaus 515. Varsinainen
     * pakattu data.
     */
    private static void writeEncodingToTheStartOfFile() throws IOException {
        for (int i = 0; i < codes.length; i++) {
            if (codes[i] != null) {
                bwriter.writeBits(BitConversions.integerAsByteString(codes[i].length()));
                bwriter.writeBits(codes[i]);
            } else {
                bwriter.writeBits("00000000");
            }
        }
    }
}
