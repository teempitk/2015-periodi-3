package Tranforms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * BWT on myös yksi datalle tehtävä muunnos, joka ei vaikuta tiedoston kokoon,
 * mutta parantaa muiden menetelmien tehokkuutta.
 *
 * @author teemupitkanen1
 */
public class BurrowsWheeler {

    private static byte[] data;

    private static int[] lines;

    private static int stringLength;

    /*
    Metodi paikalliseen pikatestailuun
    */
  //  public static void main(String[] args) throws IOException {
   //     transform("sampleFiles/abc.txt","sampleFiles/bbb.txt");
//        byte A = (byte) 'A';
//        byte B = (byte) 'B';
//        byte C = (byte) 'C';
//        data = new byte[6];
//        data[0] = B;
//        data[1] = A;
//        data[2] = C;
//        data[3] = B;
//        data[4] = A;
//        data[5] = B;
//        stringLength = 6;
//        lines = new int[6];
//        for (int i = 0; i < 6; i++) {
//            lines[i] = i;
//        }
//        int[] res = quickSortLines(lines, 0);
//        for (int i = 0; i < res.length; i++) {
//            System.out.print(res[i] + ", ");
//        }
        // oikea tulos : 4,1,3,0,5,2
  //  }

    public static void transform(String inputFile, String outputFile) throws IOException {
        System.out.println("Burrows-Wheeler -transform started");
        Path pathToOriginalData = Paths.get(inputFile);
        data = Files.readAllBytes(pathToOriginalData);
        byte[] transformedData = new byte[data.length];
        lines = new int[data.length];
        for(int i=0; i<data.length; i++){
            lines[i]=i;
        }
        stringLength = data.length;
        lines = quickSortLines(lines, 0);
        for (int i = 0; i < data.length; i++) {
            transformedData[i] = data[lines[(i - 1 + data.length) % data.length]];
        }
        FileOutputStream outStream = new FileOutputStream(new File(outputFile));
        outStream.write(transformedData);
        outStream.close();
    }

    /**
     * String quicksort tähän tilanteeseen sovellettuna (O(nlogn) average time)
     *
     * @param strings String start indices (initially 0,1,2,...)
     * @param lcp Longest common prefix of strings to be sorted (0 if not known)
     * @return Indices representing the alphabetic order of strings
     */
    public static int[] quickSortLines(int[] strings, int lcp) {
        if (strings.length <= 1 || lcp == stringLength) {
            return strings;
        }
        int pivot = strings[0];
        int b = 0;
        int s = 0;
        int e = 0;
        for (int i : strings) {
            if (data[(i + lcp) % data.length] > data[(pivot + lcp) % data.length]) {
                b++;
            } else if (data[(i + lcp) % data.length] < data[(pivot + lcp) % data.length]) {
                s++;
            } else {
                e++;
            }
        }
        int[] biggers = new int[b];
        int[] smallers = new int[s];
        int[] equals = new int[e];

        b = 0;
        s = 0;
        e = 0;
        for (int i : strings) {
            if (data[(i + lcp) % data.length] > data[(pivot + lcp) % data.length]) {
                biggers[b] = i;
                b++;
            } else if (data[(i + lcp) % data.length] < data[(pivot + lcp) % data.length]) {
                smallers[s] = i;
                s++;
            } else {
                equals[e] = i;
                e++;
            }
        }

        biggers = quickSortLines(biggers, lcp);
        smallers = quickSortLines(smallers, lcp);
        equals = quickSortLines(equals, lcp + 1);
        return joinArrays(smallers, equals, biggers);
    }

    /**
     * Liittää taulukot peräkkäin muuttamatat sisäistä järjestystä.
     *
     * @param smallers ensimmäinen osa yhdistettyä taulukkoa.
     * @param equals Keskimmäinen osa yhdistettyä taulukkoa.
     * @param biggers Viimeinen osa yhdistettyä taulukkoa.
     * @return Taulukon osat yhdistettynä.
     */
    private static int[] joinArrays(int[] smallers, int[] equals, int[] biggers) {
        int[] strings = new int[smallers.length + equals.length + biggers.length];
        for (int i = 0; i < strings.length; i++) {
            if (i < smallers.length) {
                strings[i] = smallers[i];
            } else if (i < smallers.length + equals.length) {
                strings[i] = equals[i - smallers.length];
            } else {
                strings[i] = biggers[i - smallers.length - equals.length];
            }
        }
        return strings;
    }

}
