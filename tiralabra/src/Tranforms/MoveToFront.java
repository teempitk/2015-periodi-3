package Tranforms;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
    private static List<Byte> byteList;
    /**
     * Sisältää pakattavan tiedoston bytearrayna
     */
    private static byte[] data;

    /**
     * Transform-metodi tekee muunnoksen (pakkaussuuntaan) parametrina annetusta
     * tiedostosta.
     *
     * @param inputFile Muunnettava tiedosto.
     * @param outputFile Tiedosto, johon muunnettu tieto tallennetaan.
     * @throws IOException
     */
    public static void transform(String inputFile, String outputFile) throws IOException {
        initializeVars(inputFile, outputFile);
        for (int i = 0; i < data.length; i++) {
            fstream.write((byte) mtf(data[i]));
        }
        fstream.close();
    }

    /**
     * Tekee sekä purussa että pakkauksessa tarvittavien muuttujien alustuksen.
     *
     * @param inputFile Tiedosto, josta muunnettava data luetaan.
     * @param outputFile Tiedosto, johon muunnoksen jälkeen kirjoitetaan.
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static void initializeVars(String inputFile, String outputFile) throws IOException, FileNotFoundException {
        data = Files.readAllBytes(Paths.get(inputFile));
        fstream = new FileOutputStream(outputFile);
        initializeByteList();
    }

    /**
     * Alustaa byteListin, tällä hetkellä mielivaltaiseen järjestykseen.
     */
    private static void initializeByteList() {
        byteList = new ArrayList<>();
        for (int i = -128; i < 128; i++) {
            byteList.add((byte) i);
        }
    }

    /**
     * Metodi mtf hakee byteLististä tavun indeksin, ja siirttää sitten tavun
     * listan kärkeen. Metodia käytetään muunnoksen tekemiseen pakakusvaiheessa.
     *
     * @param b Listassa siirrettävä tavu
     * @return Siirretyn tavun indeksi ennen siirtoa
     */
    private static int mtf(byte b) {
        int index = byteList.indexOf(b);
        byteList.remove(index);
        byteList.add(0, b);
        return index - 128;
    }

    /**
     * Purussa käytettävä mtf. Siirtää parametrina annetussa indeksissä olevan
     * byteListin alkion listan alkuun.
     *
     * @param index Indeksi, jossa oleva alkio siirretään listan alkuun.
     */
    private static void mtf(int index) {
        byte b = byteList.get(index);
        byteList.remove(index);
        byteList.add(0, b);
    }

    /**
     * Tekee muunnoksen "purkuvaiheessa".
     * @param inputFile Tiedosto, josta muunnettu data luetaan.
     * @param outputFile Tiedosto, johon purettu data kirjoitetaan.
     * @throws IOException 
     */
    public static void reverseTransform(String inputFile, String outputFile) throws IOException {
        initializeVars(inputFile, outputFile);
        for (int i = 0; i < data.length; i++) {
            fstream.write(byteList.get(data[i] + 128));
            mtf(data[i] + 128);
        }
        fstream.close();
    }
}
