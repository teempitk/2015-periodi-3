package Huffman;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        args = new String[3];
        args[0] = "d";
        args[1] = "sampleFiles/pakattu";
        args[2] = "sampleFiles/al.txt";
        if (args.length != 3) {
            System.out.println("Incorrect input arguments. To compress, use:\n\n"
                    + "> java Main c FileToCompress NameOfCompressedFile \n\n"
                    + "and to decompress:\n\n"
                    + "> java Main d NameOfCompressedFile NameOfDecompressedFile");
            System.exit(0);
        }
        if (args[0].equalsIgnoreCase("c")) {
            HuffmanEncoding.encode(args[1], args[2]);
        } else if (args[0].equalsIgnoreCase("d")) {
            HuffmanDecoding.decode(args[1], args[2]);
        }
    }
}
