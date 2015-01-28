package IO;

import Utils.StringBitConversions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * ByteReader on luokka pakatun tiedoston lukemiseen.
 *
 * @author teemupitkanen1
 */
public class BitReader {

    /**
     * Tiedosto, josta luetaan
     */
    private final File file;
    /**
     * FileInputStream, jonka avulla luku tapahtuu
     */
    private FileInputStream stream;
    /**
     * Koska tiedostoa luetaan tavuittain, mutta kutsuttaessa saatetaan haluta
     * vain osa biteistä, loppujen merkkijonoestitys puskuroidaan tänne.
     */
    String bitBuffer;

    /**
     * Konstruktori asettaa BitReaderin lukemaan haluttua tiedostoa
     *
     * @param file Luettava tiedosto
     * @throws java.io.IOException
     */
    public BitReader(File file) throws IOException {
        this.file = file;
        bitBuffer = "";
        try {
            stream = new FileInputStream(file);
        } catch (Exception e) {
            System.out.println("Can't read the file");
        }
    }

    /**
     * Metodilla voidaan tarkistaa, onko tiedostossa vielä lukematonta dataa.
     *
     * @return true jos dataa on lukematta, muuten false
     * @throws IOException
     */
    public boolean hasNext() throws IOException {
        return stream.available() > 0 || bitBuffer.length() > 0;
    }

    /**
     * Sulkee FileInputStreamin.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        stream.close();
    }

    /**
     * Lukee tiedoston alusta koodisanojen koodaukset, jotka tallennettu siten,
     * että koodisanan pituus on aina kahdeksassa bitissä, ja suoraan näiden
     * bittien perään on itse koodaus.
     *
     * @return seuraava koodisana tiedoston alusta.
     * @throws IOException
     */
    public String readNextCodeword() throws IOException {
        int cwlength = StringBitConversions.asByte(readBits(8));
        if (cwlength < 0) {
            cwlength += 256;
        }
        if (cwlength == 0) {
            return null;
        }
        return readBits(cwlength);
    }

    /**
     * Lukee parametrina annetun määrän bittejä, ja palauttaa niiden
     * merkkijonoesityksen. Koska tiedostoa oikeasti luetaan tavuittain,
     * ylijäämäbitit pidetään tallessa puskurissa.
     *
     * @param numberOfBits Luettavien bittien määrä
     * @return Halutut bitit merkkijonona
     * @throws IOException
     */
    public String readBits(int numberOfBits) throws IOException {
        String bits = bitBuffer;
        while (bits.length() < numberOfBits) {
            int nextByte = stream.read();
            if (nextByte < 0) {
                nextByte += 256;
            }
            bits += StringBitConversions.integerAsByteString(nextByte);
        }
        bitBuffer = bits.substring(numberOfBits);
        return bits.substring(0, numberOfBits);
    }
}
