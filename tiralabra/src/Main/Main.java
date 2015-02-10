package Main;

import Huffman.HuffmanDecoding;
import Huffman.HuffmanEncoding;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        args = new String[3];

// SAMPLE FILE1: ALICE'S ADVENTURES IN WONDERLAND (TEXT)
//        args[0] = "c";
//        args[1] = "sampleFiles/alice.txt";
//        args[2] = "sampleFiles/pakattuAlice";
//        args[0] = "d";
//        args[1] = "sampleFiles/pakattuAlice";
//        args[2] = "sampleFiles/purettuAlice.txt";

// SAMPLE FILE 2: JPG
//        args[0] = "c";
//        args[1] = "sampleFiles/turing.jpg";
//        args[2] = "sampleFiles/pakattuTuring";
//        
        args[0] = "d";
        args[1] = "sampleFiles/pakattuTuring";
        args[2] = "sampleFiles/purettuTuring.jpg";
        
        long startTime = System.nanoTime();
        
        if (args.length != 3 || !args[0].equalsIgnoreCase("c") && !args[0].equalsIgnoreCase("d")) {
            printInstructionsAndQuit();
        }
        if (args[0].equalsIgnoreCase("c")) {
            HuffmanEncoding.encode(args[1], args[2]);
        } else if (args[0].equalsIgnoreCase("d")) {
            HuffmanDecoding.decode(args[1], args[2]);
        } else {
            printInstructionsAndQuit();
        }
        long finishTime = System.nanoTime();
        System.out.println("Total runtime: "+(finishTime-startTime)*1.0e-9+" sec");
    }

    private static void printInstructionsAndQuit() {
        System.out.println("Incorrect input arguments. To compress, use:\n\n"
                + "> java Main c FileToCompress NameOfCompressedFile \n\n"
                + "and to decompress:\n\n"
                + "> java Main d NameOfCompressedFile NameOfDecompressedFile");
        System.exit(0);
    }
}
