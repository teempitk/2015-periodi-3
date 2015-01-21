package IO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * BitWriter-luokka huolehtii datan kirjoituksen bittitasolla tiedostoon.
 * BitWriterista luodaan ensin instanssi, jolle sitten kutsutaan writeBits-
 * metodia, joka kirjoittaa parametrina annetun '0' ja '1' -merkkejä sisältävän
 * datan yksittäisinä bitteinä tiedostoon.
 *
 * @author teemupitkanen1
 */
public class BitWriter {

    /**
     * Tiedosto,johon bitit kirjoitetaan.
     */
    private File file;
    /**
     * Bitit täytyy kirjoittaa tiedostoon tavuittain. Jos writeBits-metodin
     * parametrina saatua bittijonoa ei voida kirjoittaa tasatavuina, ylijääneet
     * alle kahdeksan bittiä pidetään merkkijonona tallessa tässä muuttujassa.
     */
    private String notWrittenYet;
    /**
     * Kukin luvuista vastaa tavua, jossa vain (taulukon indeksi +1):s bitti on
     * 1
     */
    private byte[] powersOfTwo = {1, 2, 4, 8, 16, 32, 64, -128};
    /**
     * Stream bytejen kirjoittamiseen tiedostoon
     */
    private FileOutputStream stream;

    /**
     * Konstruktorissa asetetaan tiedosto, johon BitWriter kirjoittaa. Yksi
     * instanssi on siis aina sidottu samaan tiedostoon.
     *
     * @param file Tiedosto johon kirjoitus tapahtuu.
     */
    public BitWriter(File file) {
        this.file = file;
        notWrittenYet = "";
        try {
            stream = new FileOutputStream(file);
        } catch (Exception e) {
            System.out.println("Haluttuun kohdetiedostoon ei voida kirjoittaa!");
            System.exit(0);
        }
    }

    /**
     * Luokan tärkein metodi, joka kirjoittaa parametrina saadun merkkijonon,
     * esim "0101001101000101" tiedostoon yksittäisinä bitteinä
     *
     * @param bitString Kirjoitettava bittijono
     * @throws java.io.IOException
     */
    public void writeBits(String bitString) throws IOException {
        bitString = notWrittenYet + bitString;
        int len = bitString.length();
        int alreadyWritten = 0;
        while (len-alreadyWritten >= 8) {
            byte B = asByte(bitString.substring(alreadyWritten, alreadyWritten + 8));
            alreadyWritten += 8;
            stream.write(B);
        }
        notWrittenYet = bitString.substring(alreadyWritten);
    }

    /**
     * Huolehtii notWrittenYet-bittien lisäämisestä tiedoston loppuun.
     * @param EOFbits Bittijono, josta tiedoston loppu tunnistetaan. Loput bitit
     * tasabyteihin pääsemiseksi täytetään nollilla.
     * @throws java.io.IOException
     */
    public void writeTheLastBits(String EOFbits) throws IOException {
        String bitString = notWrittenYet + EOFbits;
        int numberOfZeroBits = (8-(bitString.length()%8))%8;
        for (int i=0; i<numberOfZeroBits; i++){
            bitString +="0";
        }
        writeBits(bitString);
        stream.close();
    }

    /**
     * Muuttaa 8-merkkisen String-esityksen tavusta yhdeksi byteksi.
     *
     * @param bits merkkijonoesitys tavvusta
     * @return tavu, jossa bitit merkkijonon mukaisesti.
     */
    private byte asByte(String bits) {
        byte B = 0;
        for (int i = 0; i < 8; i++) {
            if (bits.charAt(i) == '1') {
                B = (byte) (B | powersOfTwo[7-i]);
            }
        }
        return B;
    }

}
