package Huffman;

import IO.BitWriter;
import Utils.BitConversions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
     * Luokan ydinmetodi, jota kutsumalla tiedoston pakkaus tapahtuu.
     *
     * @param inFile Pakattava tiedosto
     * @param outFile Pakatun tiedoston nimi
     * @throws java.io.IOException
     */
    public static void encode(String inFile, String outFile) throws IOException {
        countDifferentBytesInFile(inFile);

        codes = HuffmanTree.huffmanCodewords(freqs);

        bwriter = new BitWriter(new File(outFile));
        writeEncodingToTheStartOfFile();

        writeToFileAsBits(inFile);
        bwriter.writeTheLastBits(codes[256]);
    }

    /**
     * Kun tavuittaiset koodisanat on selvitetty, tämä metodi huolehtii koko
     * tekstin koodauksesta ja tallentamisesta tiedostoon.
     *
     * @param outFile Kohdetiedosto
     * @throws IOException
     */
    private static void writeToFileAsBits(String inFile) throws IOException {
        byte[] data = Files.readAllBytes(Paths.get(inFile));
        for (int i = 0; i < data.length; i++) {
            int byteToWrite = data[i];
            if (byteToWrite < 0) {
                byteToWrite += 256;
            }
            bwriter.writeBits(codes[byteToWrite]);
        }
    }

    /**
     * Laskee kunkin erilaisen tavun esiintymismäärät pakattavassa tiedostossa.
     */
    private static void countDifferentBytesInFile(String inFile) throws IOException {
        byte[] data = Files.readAllBytes(Paths.get(inFile));
        freqs = new int[257];
        freqs[256] = 1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                freqs[data[i] + 256]++;
            } else {
                freqs[data[i]]++;
            }

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
