package Tranforms;

import DataStructures.MyLinkedList;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    private static MyLinkedList<Byte> byteList;
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

        System.out.println("Move to front transformation started");
        long mtfStartTime = System.nanoTime();

        long initStartTime = System.nanoTime();
        System.out.print("Phase 1/2. Initializing variables ... ");
        initializeVars(inputFile, outputFile);
        System.out.println("\t\t Finished. Time: " + String.format("%.3f", (System.nanoTime() - initStartTime) * 1.0e-9) + " sec");

        long replacingStartTime = System.nanoTime();
        System.out.print("Phase 2/2. Replacing bytes with indices ... ");
        for (int i = 0; i < data.length; i++) {
            fstream.write((byte) mtf(data[i]));
        }
        fstream.close();
        System.out.println("\t Finished. Time: " + String.format("%.3f", (System.nanoTime() - replacingStartTime) * 1.0e-9) + " sec");

        System.out.println("Move to front transform finished. Total time: " + (System.nanoTime() - mtfStartTime) * 1.0e-9 + " sec");
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
        byteList = new MyLinkedList<>();
        for (int i = 0; i < 256; i++) {
            int b = i;
            if(b>127)b-=256;
            byteList.addLast((byte) b);
        }
    }

    /**
     * Metodi mtf hakee byteLististä tavun indeksin, ja siirttää sitten tavun
     * listan kärkeen. Metodia käytetään muunnoksen tekemiseen pakkausvaiheessa.
     *
     * @param b Listassa siirrettävä tavu
     * @return Siirretyn tavun indeksi ennen siirtoa
     */
    private static int mtf(byte b) {
        int index = byteList.indexOf(b);
        byteList.removeAtIndex(index);
        byteList.addFirst(b);
        if (index > 127) {
            index -= 256;
        }
        return index;
    }

    /**
     * Purussa käytettävä mtf. Siirtää parametrina annetussa indeksissä olevan
     * byteListin alkion listan alkuun.
     *
     * @param index Indeksi, jossa oleva alkio siirretään listan alkuun.
     */
    private static void mtf(int index) {
        byte b = byteList.getElementAtIndex(index);
        byteList.removeAtIndex(index);
        byteList.addFirst(b);
    }

    /**
     * Tekee muunnoksen "purkuvaiheessa".
     *
     * @param inputFile Tiedosto, josta muunnettu data luetaan.
     * @param outputFile Tiedosto, johon purettu data kirjoitetaan.
     * @throws IOException
     */
    public static void reverseTransform(String inputFile, String outputFile) throws IOException {
        System.out.println("Move to front transform started");
        long mtfStartTime = System.nanoTime();

        long initStartTime = System.nanoTime();
        System.out.print("Phase 1/2. Initializing variables ... ");
        initializeVars(inputFile, outputFile);
        System.out.println("\t\t Finished. Time: " + String.format("%.3f", (System.nanoTime() - initStartTime) * 1.0e-9) + " sec");

        System.out.print("Phase 2/2. Replacing bytes with indices ... ");
        long replacingStartTime = System.nanoTime();
        for (int i = 0; i < data.length; i++) {
            int index = data[i];
            if (index < 0) {
                index += 256;
            }
            fstream.write(byteList.getElementAtIndex(index));
            mtf(index);
        }
        fstream.close();
        System.out.println("\t Finished. Time: " + String.format("%.3f", (System.nanoTime() - replacingStartTime) * 1.0e-9) + " sec");

        System.out.println("Move to front transform finished. Total time: " + (System.nanoTime() - mtfStartTime) * 1.0e-9 + " sec");

    }
}
