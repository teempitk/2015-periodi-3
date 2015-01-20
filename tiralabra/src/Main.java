
import java.io.File;
import java.util.Scanner;

public class Main {

    private static int[] freqs;
    private static int nonascii;

    public static void main(String[] args) {
        nonascii = 0;
        freqs = new int[256];
        for (int i = 0; i < 256; i++) {
            freqs[i] = 0;
        }
        if(!args[0].substring(args[0].length()-4).equals(".txt")){
            System.out.println("At the moment, only .txt -files can be compressed");
            System.exit(0);
        }
        Scanner scan = null;
        try {
            scan = new Scanner(new File(args[0]));
        } catch (Exception e) {
            System.out.println("No such file");
            System.exit(0);
        }
        while (scan.hasNext()) {
            countSymbolsInOneLine(scan.nextLine());
        }
        if (nonascii > 0) {
            System.out.println("Text contains " + nonascii + " characters not"
                    + " included in 8-bit ASCII. Those are lost in the compression.");
        }
        scan.close();
        String[] codes = HuffmanTree.huffmanCodewords(freqs);
        for (int i = 0; i < 256; i++) {
            System.out.println(i + ":" + codes[i]);
        }
    }

    private static void countSymbolsInOneLine(String line) {
        for (int i = 0; i < line.length(); i++) {
            char index = line.charAt(i);
            if (index < 256) {
                freqs[index]++;
            } else {
                nonascii++;
                System.out.println(line);
            }
        }
        freqs[10]++; // Rivi päättyy aina rivinvaihtoon.
    }
}
