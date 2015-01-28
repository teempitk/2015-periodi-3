package IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * ByteReader on luokka pakattavan tiedoston lukuun tavu kerrallaan. Luokan
 * toteutus perustuu java.io.FileInputStreamiin
 *
 * @author teemupitkanen1
 */
public class ByteReader {

    /**
     * Tiedosto, josta luetaan
     */
    private final File file;
    /**
     * FileInputStream, jonka avulla luku tapahtuu
     */
    private FileInputStream stream;

    /**
     * Konstruktori asettaa ByteReaderin lukemaan haluttua tiedostoa
     *
     * @param file Luettava tiedosto
     */
    public ByteReader(File file) {
        this.file = file;
        try {
            stream = new FileInputStream(file);
        } catch (Exception e) {
            System.out.println("Can't read the file");
        }
    }

    /**
     * Lukee tavun verran dataa tiedostosta. Paluumuoto on luku välillä 0...255
     * @return luetun byten arvo + 128
     * @throws IOException
     */
    public int readByte() throws IOException {
        return stream.read();
    }

    /**
     * Metodilla voidaan tarkistaa, onko tiedostossa vielä lukematonta dataa.
     *
     * @return true jos dataa on lukematta, muuten false
     * @throws IOException
     */
    public boolean hasNext() throws IOException {
        return stream.available() > 0;
    }

    /**
     * Sulkee FileInputStreamin.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        stream.close();
    }
}
