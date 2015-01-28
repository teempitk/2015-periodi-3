package Huffman;

import IO.BitWriter;
import IO.ByteReader;
import java.io.File;
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
     * Taulukkoon kerätään tieto kunkin merkin esiintymiskerroista Huffman-
     * koodausta varten
     */
    private static int[] freqs;
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
     * Pakattavan tiedoston lukemiseen käytettävä ByteReader
     */
    private static ByteReader breader;
    /**
     * Luokan ydinmetodi, jota kutsumalla tiedoston pakkaus tapahtuu.
     *
     * @param inFile Pakattava tiedosto
     * @param outFile Pakatun tiedoston nimi
     */
    public static void encode(String inFile, String outFile) throws IOException {
        freqs = new int[256];
        try {
            breader = new ByteReader(new File(inFile));
        } catch (Exception e) {
            System.out.println("No such file");
            System.exit(0);
        }
        countSymbols();
        breader.close();
        codes = HuffmanTree.huffmanCodewords(freqs);
        bwriter = new BitWriter(new File(outFile));
        writeToFileAsBits(inFile, outFile);

    }

    /**
     * Kun tavuittaiset koodisanat on selvitetty, tämä metodi huolehtii koko
     * tekstin koodauksesta ja tallentamisesta tiedostoon.
     *
     * @param outFile Kohdetiedosto
     * @throws IOException
     */
    private static void writeToFileAsBits(String inFile, String outFile) throws IOException {
        breader = new ByteReader(new File(inFile));
        while (breader.hasNext()){
            bwriter.writeBits(codes[breader.readByte()]);
        }
        bwriter.writeTheLastBits("");
        breader.close();
    }

    /**
     * EI ENÄÄ KÄYTÖSSÄ!!!EI ENÄÄ KÄYTÖSSÄ!!!EI ENÄÄ KÄYTÖSSÄ!!!EI ENÄÄ KÄYTÖSSÄ!!!
     * Metodi tarkistaa, onko pakattava tiedosto tekstitiedosto, ja keskeyttää
     * ohjelman suorituksen jos ei.
     *
     * @param inFile Pakattavan tiedoston nimi
     */
//    private static void checkFileType(String inFile) {
//        if (!inFile.substring(inFile.length() - 4).equals(".txt")) {
//            System.out.println("At the moment, only .txt -files can be compressed");
//            System.exit(0);
//        }
//    }

    /**
     * Laskee kunkin erilaisen tavun esiintymismäärät pakattavassa tiedostossa.
     */
    private static void countSymbols() throws IOException {
        while(breader.hasNext()){
            freqs[breader.readByte()]++;
        }
    }

    /**
     * EI ENÄÄ KÄYTÖSSÄ!!!EI ENÄÄ KÄYTÖSSÄ!!!EI ENÄÄ KÄYTÖSSÄ!!!EI ENÄÄ KÄYTÖSSÄ!!!
     * Laskee kunkin merkin esiintymismäärät yhdellä rivillä.
     *
     * @param line Rivi, joka lasketaan.
     */
//    private static void countSymbolsInOneLine(String line) {
//        for (int i = 0; i < line.length(); i++) {
//            char index = line.charAt(i);
//            if (index < 256) {
//                freqs[index]++;
//            } else {
//               // nonascii++;
//            }
//        }
//        freqs[10]++; // Rivi päättyy aina rivinvaihtoon.
//    }
}
