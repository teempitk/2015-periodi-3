package Tranforms;

import Utils.BitConversions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * BWT on myös yksi datalle tehtävä muunnos, joka ei vaikuta tiedoston kokoon,
 * mutta parantaa muiden menetelmien tehokkuutta.
 *
 * @author teemupitkanen1
 */
public class BurrowsWheeler {

    /**
     * Bytearray, johon syötetiedosto luetaan.
     */
    private static byte[] data;
    /**
     * Sisältää "kaikki datan sanat", data.length numeroa, jotka kukin vastaavat
     * kyseisestä datan indeksistä alkavaa koko datan pituista sanaa (mukana
     * kierto alkuun, eli jokainen "sana" sisältää datan kaikki merkit). Näiden
     * järjestäminen on BWT:n merkittävä vaihe.
     */
    private static int[] lines;

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
        readInputData(inputFile);
        byte[] transformedData = new byte[data.length];
        lines = new int[data.length];
        int lastByteInOriginalDataPointer = 0;
        for (int i = 0; i < data.length; i++) {
            lines[i] = i;
        }
        lines = quickSortLines(lines, 0);
        for (int i = 0; i < data.length; i++) {
            transformedData[i] = data[(lines[i] - 1 + data.length) % data.length];
            if (lines[i] == 0) {
                lastByteInOriginalDataPointer = i;
            }
        }
        FileOutputStream outStream = new FileOutputStream(new File(outputFile));
        outStream.write(BitConversions.intToByteArray(lastByteInOriginalDataPointer));
        outStream.write(transformedData);
        outStream.close();
    }

    private static void readInputData(String inputFile) throws IOException {
        System.out.println("Burrows-Wheeler -transform started");
        Path pathToOriginalData = Paths.get(inputFile);
        data = Files.readAllBytes(pathToOriginalData);
    }

    /**
     * String quicksort tähän tilanteeseen sovellettuna (O(nlogn) average time)
     *
     * @param strings String start indices (initially 0,1,2,...)
     * @param lcp Longest common prefix of strings to be sorted (0 if not known)
     * @return Indices representing the alphabetic order of strings
     */
    public static int[] quickSortLines(int[] strings, int lcp) {
        if (strings.length <= 1 || lcp == data.length) {
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

    public static void inverseTransform(String inputFile, String outputFile) throws IOException {
        readInputData(inputFile);
        int indexOfLastCharInOriginalData = readIndexOfLast();
        byte[] sorted = Arrays.copyOf(data, data.length);
        Arrays.sort(sorted);

        // Tässä osassa taulukon i:nnessä indeksissä on lista, jossa on i:nnen tavun (esim 1. tavu on 00000001) esiintymien indeksit muunnetussa datassa. Tätä tarvitaan myöhemmin.
        LinkedList<Integer>[] arr = new LinkedList[256];
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            int index = b;
            if (index < 0) {
                index += 256;
            }
            LinkedList list = arr[index];
            if (list == null) {
                list = new LinkedList();
                arr[index] = list;
            }
            list.addLast(i);
        }

        //Seuraava osa selvittää kullekin järjestetyn taulukon tavulle edeltävän tavun sijainnin muunnetussa datassa.
        int[] previousLetters = new int[data.length];
        for (int i = 0; i < sorted.length; i++) {
            byte b = sorted[i];
            int index = b;
            if (index < 0) {
                index += 256;
            }
            LinkedList<Integer> list = arr[index];
            previousLetters[i] = list.getFirst();
            list.remove();
        }

        // Sitten vain luetaan alkuperäinen data edellä luodun taulukon avulla
        byte[] originalData = new byte[data.length];
        int indexOfCurr = indexOfLastCharInOriginalData;
        for (int i = 0; i < data.length; i++) {
            originalData[data.length - i - 1] = data[indexOfCurr];
            indexOfCurr = previousLetters[indexOfCurr];
        }

        FileOutputStream outStream = new FileOutputStream(new File(outputFile));
        outStream.write(originalData);
        outStream.close();

    }

    private static int readIndexOfLast() {
        byte[] numberAsByteArray = {data[0], data[1], data[2], data[3]};
        int number = BitConversions.byteArrayToInt(numberAsByteArray);
        byte[] newData = new byte[data.length - 4];
        for (int i = 0; i < newData.length; i++) {
            newData[i]=data[i+4];
        }
        data=newData;
        return number;
    }
}
