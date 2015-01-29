package Huffman;

import IO.BitReader;
import IO.BitWriter;
import Utils.BitConversions;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * HuffmanDecoder huolehtii pakattujen tiedostojen purkamisesta. Luokan ydin on
 * staattinen metodi decode. Pääpiirteissään purku tapahtuu siten, että
 * decode-metodi kutsuu ensin readCodewordsFromFile-metodia, ja saa siten
 * selville Huffman-koodauksen koodisanat. Tämän jälkeen kutsutaan
 * decompress-metodia, joka suorittaa varsinaisen datan purkamisen.
 *
 * @author teemupitkanen1
 */
public class HuffmanDecoding {

    /**
     * HashMapiin luetaan tiedoston alusta koodisanat. Avaimena on aina
     * tiedostosta luettu bittijono, joten hajautuksen avulla voidaan nopeasti
     * tarkistaa, vastaako lukemisessa "kertynyt" bittijono jotain koodisanaa.
     * Jos vastaa, vastaava tavu löytyy myös nopeasti.
     */
    private static Map<String, String> codes;

    /**
     * Tiedoston bitittäiseen lukuun käytettävä BitReader.
     */
    private static BitReader breader;
    /**
     * Puretun tiedoston kirjoittava BitWriter.
     */
    private static BitWriter bwriter;

    /**
     * decode-metodi saa parametrina purettavan tiedoston, ja tiedoston johon
     * puretaan. Metodi lukee ensin tiedoston alusta koodisanat, ja suorittaa
     * sitten varsinaisen datan purun niitä käyttäen.
     *
     * @param inFile Pakattu tiedosto, jota puretaan
     * @param outFile Tiedosto, johon puretaan
     */
    public static void decode(String inFile, String outFile) throws IOException {
        breader = new BitReader(new File(inFile));
        bwriter = new BitWriter(new File(outFile));
        codes = new HashMap();
        readCodewordsFromFile();
        decompress();
        breader.close();
        bwriter.writeTheLastBits("");
    }

    /**
     * Käytetään ensimmäisenä purussa, lukee kaikki koodisanat tiedoston alusta
     * ja tallentaa ne mappiin käyttöä varten.
     *
     * @throws IOException
     */
    private static void readCodewordsFromFile() throws IOException {
        for (int i = 0; i < 257; i++) {
            String codeword = breader.readNextCodeword();
            if (codeword != null) {
                if (i == 256) {
                    codes.put(codeword, "EOF");
                } else {
                    codes.put(codeword, BitConversions.integerAsByteString(i));
                }
            }
        }
    }

    /**
     * Metodi purkaa varsinaisen datan, käyttämällä aiemmin purettuja
     * koodisanoja.
     *
     * @throws IOException
     */
    private static void decompress() throws IOException {
        String bits = "";
        while (breader.hasNext()) {
            bits += breader.readBits(1);
            if (codes.containsKey(bits)) {
                if (codes.get(bits).equals("EOF")) {
                    break;
                }
                bwriter.writeBits(codes.get(bits));
                bits = "";
            }
        }
    }
}
