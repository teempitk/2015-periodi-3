
package IO;

import Utils.StringBitConversions;
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
        
        Scanner scanorig = new Scanner(new File("sampleFiles/alice.txt"));
        Scanner scannernew = new Scanner(new File("sampleFiles/purettuAlice.txt"));
        while (scanorig.hasNext() && scannernew.hasNext()){
            String line1=scanorig.nextLine();
            String line2=scannernew.nextLine();
            if(!line1.equals(line2)){
                System.out.println(line1);
                System.out.println(line2);
            }
        }
        while(scanorig.hasNext()){
            System.out.println(scanorig.nextLine());
        }
        
        
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
