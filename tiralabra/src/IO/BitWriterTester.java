
package IO;

import Utils.BitConversions;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * TÄMÄ EI TULE JÄÄMÄÄN LOPULLISEEN TOTEUTUKSEEN. IDEANA VAIN NOPEASTI TESTATA, ETTÄ
 * BITTIEN KIRJOITUS TIEDOSTOON TOIMII JOTAKUINKIN OIKEIN!
 * @author teemupitkanen1
 */
public class BitWriterTester {
    public static void main(String[] args) throws IOException{
        
        File file = new File("test.txt");
        BitWriter writer = new BitWriter(file);
        writer.writeBits("01");
        writer.writeTheLastBits("");
        
//        File file = new File("outputfiles/merkkitiedosto.txt");
//        BitWriter writer = new BitWriter(file);
//        writer.writeBits("01000001");//A
//        writer.writeBits("01000010");//B
//        writer.writeBits("0100000101000010");
//        writer.writeBits("010000");writer.writeBits("1101000100");//C&D
//        writer.writeBits("0100001");//B - LAST BIT (nollabitti täydentyy seuraavalla rivillä)
//        writer.writeTheLastBits("");
        
    }
}
