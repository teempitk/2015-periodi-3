package Main;

import Huffman.HuffmanDecoding;
import Huffman.HuffmanEncoding;
import Tranforms.BurrowsWheeler;
import Tranforms.MoveToFront;
import java.io.File;
import java.io.IOException;

public class Main {

    private static long startTime;

    private static File temp;
    private static File temp2;

    private static File input;

    private static File output;

    public static void main(String[] args) throws IOException {
        startTime = System.nanoTime();
        temp = new File("RemovableTemporaryFileForCompressionIntermediateResults");
        temp2 = new File("RemovableTemporaryFileForCompressionIntermediateResults2");

        if (args.length != 3) {
            printInstructionsAndQuit();
        }
        input = new File(args[1]);
        output = new File(args[2]);
        
        if (args[0].equalsIgnoreCase("c")) {
            compress(args);
        } else if (args[0].equalsIgnoreCase("d")) {
            decompress(args);
        } else {
            printInstructionsAndQuit();
        }
        temp.delete();
        temp2.delete();
    }

    private static void decompress(String[] args) throws IOException {
        System.out.println("Decompression started.");

        HuffmanDecoding.decode(args[1], temp.getName());
        MoveToFront.reverseTransform(temp.getName(), args[2]);

        long finishTime = System.nanoTime();
        System.out.println("Decompression finished. Total time: " + (finishTime - startTime) * 1.0e-9 + " sec.");
    }

    private static void compress(String[] args) throws IOException {
        System.out.println("Compression started.");
        BurrowsWheeler.transform(args[1], temp2.getName());
        MoveToFront.transform(temp2.getName(), temp.getName());
        HuffmanEncoding.encode(temp.getName(), args[2]);
        long finishTime = System.nanoTime();
        System.out.println("Compression finished. Total time: " + (finishTime - startTime) * 1.0e-9 + " sec.");
        System.out.println("File size reduced from "+input.length() + " bytes to "+output.length()+" bytes ("+String.format("%.1f", 100.0*output.length()/input.length())+"% of original size).");
    }

    private static void printInstructionsAndQuit() {
        System.out.println("Incorrect input arguments. To compress, use:\n\n"
                + "> java Main c FileToCompress NameOfCompressedFile \n\n"
                + "and to decompress:\n\n"
                + "> java Main d NameOfCompressedFile NameOfDecompressedFile");
        System.exit(0);
    }
}
