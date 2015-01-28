package Huffman;

import IO.BitReader;
import IO.BitWriter;
import Utils.StringBitConversions;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * HuffmanDecoder huolehtii pakattujen tiedostojen purkamisesta. Luokan ydin on
 * staattinen metodi decode.
 *
 * @author teemupitkanen1
 */
public class HuffmanDecoding {

    /**
     * HashMapiin luetaan tiedoston alusta koodisanat. Avaimena on aina
     * tiedostosta luettu bittijono, joten voidaan nopeasti tarkistaa, vastaako
     * lukemisessa "kertynyt" bittijono jotain koodisanaa. Jos vastaa, vastaava
     * tavu löytyy myös nopeasti.
     */
    private static Map<String, String> codes;

    /**
     * Tiedoston bitittäiseen lukuun käytettävä BitReader.
     */
    private static BitReader breader;
    /**
     * Puretun tiedoston kirjoittava BitWriter
     */
    private static BitWriter bwriter;
    /**
     * Bittijono, joka merkkaa tiedoston loppua.
     */
//    private static String eofmarker;                                          ei käytössä

    /**
     * decode-metodi saa parametrina purettavan tiedoston, ja tiedoston johon
     * puretaan.Metodi lukee ensin tiedoston alusta koodisanat, ja suorittaa
     * sitten purun niitä käyttäen.
     *
     * @param inFile Pakattu tiedosto, jota puretaan
     * @param outFile Tiedosto, johon puretaan
     */
    public static void decode(String inFile, String outFile) throws IOException {
        codes = new HashMap();
        breader = new BitReader(new File(inFile));
        bwriter = new BitWriter(new File(outFile));
        readCodewordsFromFile();
        decompress();
        breader.close();
        bwriter.writeTheLastBits("");
    }

    private static void readCodewordsFromFile() throws IOException {
        for (int i = 0; i < 257; i++) {
            String codeword = breader.readNextCodeword();
            if (codeword != null) {
                codes.put(codeword, StringBitConversions.positiveIntegerAsOneByteBitstring(i));
//                System.out.println(i + ": " + codeword);
//            } else {
//                System.out.println(i);
            }
        }
    }

    private static void decompress() throws IOException {
        String bits = "";
        while(breader.hasNext()){
            bits+=breader.readBits(1);
            if(codes.containsKey(bits)){
                bwriter.writeBits(codes.get(bits));
                bits="";
            }
        }
    }
}
