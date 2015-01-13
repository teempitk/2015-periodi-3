
import java.io.File;
import java.util.Scanner;

public class Main {

    private static int[] freqs;
    
    public static void main(String[] args) {
        freqs=new int[128];
        for (int i=0; i<128; i++){
            freqs[i]=0;
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
        scan.close();
        HuffmanTree huff = new HuffmanTree(freqs);
        String[] codes = huff.getCodewords();
        for (int i = 0; i< 128; i++){
            System.out.println(i +":"+ codes[i]);
        }
    }

    private static void countSymbolsInOneLine(String line) {
        for(int i=0; i< line.length(); i++){
            char index = line.charAt(i);
            freqs[index]++;
        }
    }
}
