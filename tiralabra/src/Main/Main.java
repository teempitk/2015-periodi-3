package Main;

import Huffman.HuffmanDecoding;
import Huffman.HuffmanEncoding;
import Tranforms.MoveToFront;
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
//        args[0] = "d";
//        args[1] = "sampleFiles/pakattuTuring";
//        args[2] = "sampleFiles/purettuTuring.jpg";
//  SAMPLE FILE 3: U00096.2.fas (Genomic data ~1MB)
//        args[0] = "c";
//        args[1] = "sampleFiles/U00096.2.fas";
//        args[2] = "sampleFiles/U00096.2.fas.pakattu";
        args[0] = "d";
        args[1] = "sampleFiles/U00096.2.fas.pakattu";
        args[2] = "sampleFiles/U00096.2.fas.purettu";
        
        long startTime = System.nanoTime();
        
        if (args.length != 3) {
            printInstructionsAndQuit();
        }
        if (args[0].equalsIgnoreCase("c")) {
            MoveToFront.transform(args[1], "sampleFiles/temp");
            HuffmanEncoding.encode("sampleFiles/temp", args[2]);
        } else if (args[0].equalsIgnoreCase("d")) {
            HuffmanDecoding.decode(args[1], "sampleFiles/temp");
            MoveToFront.reverseTransform("sampleFiles/temp", args[2]);
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
