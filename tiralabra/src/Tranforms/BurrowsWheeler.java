package Tranforms;

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

    public static void main(String[] args){
        byte A = (byte)'A';
        byte B = (byte)'B';
        byte C = (byte)'C';
        data = new byte[6];
        data[0]=B;
        data[1]=A;
        data[2]=C;
        data[3]=B;
        data[4]=A;
        data[5]=B;
        stringLength = 6;
        lines = new int[6];
        for(int i=0;i<6;i++){
            lines[i]=i;
        }
        int[] res = quickSortLines(lines,0);
        for(int i=0;i<res.length;i++){
            System.out.print(res[i]+", ");
        }
        // oikea tulos : 4,1,3,5,2
    }
    
    public static void transform(String inputFile, String outputFile) throws IOException {
        Path pathToOriginalData = Paths.get(inputFile);
        data = Files.readAllBytes(pathToOriginalData);
        lines = new int[data.length];
        stringLength = data.length;
        for (int i = 0; i < lines.length; i++) {
            lines[i] = i;
        }
        quickSortLines(lines, 0);
    }

    /**
     * String quicksort tähän tilanteeseen sovellettuna (O(nlogn) average time)
     * @param strings String start indices (initially 0,1,2,...)
     * @param lcp Longest common prefix of strings to be sorted (0 if not known)
     * @return Indices representing the alphabetic order of strings
     */
    public static int[] quickSortLines(int[] strings, int lcp) {
        int pivot = 0;
        if (strings.length <= 1 || lcp == stringLength) {
            return strings;
        }
        int numberOfBiggers = 0;
        int numberOfSmallers = 0;
        int numberOfEquals = 0;
        
        for (int i = 0; i < data.length; i++) {
            if (i != pivot) {
                if (data[(i + lcp)%data.length] > data[(pivot + lcp)%data.length]) {
                    numberOfBiggers++;
                } else if (data[(i + lcp)%data.length] < data[(pivot + lcp)%data.length]) {
                    numberOfSmallers++;
                } else {
                    numberOfEquals++;
                }
            }
        }
        System.out.println("B:"+numberOfBiggers+", S: "+numberOfSmallers);
        int[] biggers = new int[numberOfBiggers];
        int[] smallers = new int[numberOfSmallers];
        int[] equals = new int[numberOfEquals];

        numberOfBiggers = 0;
        numberOfSmallers = 0;
        numberOfEquals = 0;

        for (int i = 0; i < stringLength; i++) {
            if (i != pivot) {
                if (data[i + lcp] > data[pivot + lcp]) {
                    biggers[numberOfBiggers] = i;
                    numberOfBiggers++;
                } else if (data[i + lcp] < data[pivot + lcp]) {
                    smallers[numberOfSmallers] = i;
                    numberOfSmallers++;
                } else {
                    equals[numberOfEquals] = i;
                    numberOfEquals++;
                }
            }
        }
        biggers = quickSortLines(biggers,lcp);
        smallers = quickSortLines(smallers,lcp);
        equals = quickSortLines(equals,lcp+1);

        strings=new int[strings.length];
        
        for(int i=0;i<strings.length;i++){
            if (i<numberOfSmallers){
                strings[i]=smallers[i];
            }else if(i<numberOfSmallers + numberOfEquals){
                strings[i]=equals[i-numberOfSmallers];
            }else{
                strings[i]=biggers[i-numberOfSmallers-numberOfEquals];
            }
        }
        return strings;
    }

}
