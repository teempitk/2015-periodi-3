package Tranforms;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * Move to front on pakkauksessa käytettävä menetelmä, joka ei varsinaisesti
 * pakkaa dataa, mutta tehostaa muiden menetelmien toimintaa. Kaikki mahdolliset
 * tavut (0-255) ovat ensin listassa. Sitten yksi tavu kerrallaan, tiedostosta
 * luettu tavu korvataan tavun indeksillä listassa. Tämän jälkeen tavu siiretään
 * listan kärkeen (move to front). Usein toistuvat tavut pysyvät tavulistan
 * alkupäässä, ja pienet indeksit esiintyvät tiedostossa paljon. Tämä taas
 * tehostaa esim. ennen Huffman-koodausta tehtynä itse pakakuksen tehokkuutta.
 *
 * @author teemupitkanen1
 */
public class MoveToFront {

    /**
     * fstreamilla hoidetaan muunnettujen tavujen kirjoittaminen tiedostoon.
     */
    private static FileOutputStream fstream;
    /**
     * byteList sisältää kaikki mahdolliset tavut siinä järjestyksessä, mikä
     * niistä on esiintyntyt lähdetiedostossa viimeksi.
     */
    private static LinkedList<Byte> byteList;
    /**
     * Sisältää pakattavan tiedoston bytearrayna
     */
    private static byte[] data;
    
    /**
     * Transform-metodi tekee muunnoksen parametrina annetusta tiedostosta.
     *
     * @param inputFile Muunnettava tiedosto.
     * @param outputFile Tiedosto, johon muunnettu tieto tallennetaan.
     * @throws IOException
     */
    public static void transform(String inputFile, String outputFile) throws IOException {
        Path pathToOriginalData = Paths.get(inputFile);
        data = Files.readAllBytes(pathToOriginalData);
        fstream = new FileOutputStream(outputFile);
        initializeByteList();
        for (int i = 0; i < data.length; i++) {
            fstream.write((byte) mtf(data[i]));
        }
        fstream.close();
    }

    /**
     * Alustaa byteListin, tällä hetkellä mielivaltaiseen järjestykseen.
     */
    private static void initializeByteList() {
        byteList = new LinkedList();
        for (int i = -128; i < 128; i++) {
            byteList.add((byte) i);
        }
    }

    /**
     * Metodi mtf hakee byteLististä tavun indeksin, ja siirttää sitten tavun
     * listan kärkeen.
     *
     * @param originalData
     * @param i
     * @return
     */
    private static int mtf(byte b) {
        int index = byteList.indexOf(b);
        byteList.remove(index);
        byteList.add(0, b);
        return index - 128;
    }
}
