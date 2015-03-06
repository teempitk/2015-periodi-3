package Main;

import Huffman.HuffmanDecoding;
import Huffman.HuffmanEncoding;
import Tranforms.BurrowsWheeler;
import Tranforms.MoveToFront;
import Utils.ArrayUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static long startTime;

    private static File temp;
    private static File temp2;

    private static String inputFileName;
    private static String outputFileName;

    private static File input;

    private static File output;

    private static boolean printStats;

    public static void main(String[] args) throws IOException {
        startTime = System.nanoTime();
        temp = new File("RemovableTemporaryFileForCompressionIntermediateResults");
        temp2 = new File("RemovableTemporaryFileForCompressionIntermediateResults2");

        if (args.length < 2) {
            printInstructionsAndQuit();
        }

        String[] flags = Arrays.copyOf(args, args.length - 1);
        printStats = ArrayUtils.contains(flags, "-p");
        setInputAndOutputFiles(args, flags);
        if (ArrayUtils.contains(flags, "-c")) {
            if (printStats) {
                compressWithPrinting(args);
            } else {
                compress(args);
            }
        } else if (ArrayUtils.contains(flags, "-d")) {
            if (printStats) {
                decompressWithPrinting(args);
            } else {
                decompress(args);
            }

        }
        temp.delete();
        temp2.delete();
    }

    private static void setInputAndOutputFiles(String[] args, String[] flags) {
        inputFileName = args[args.length - 1];
        outputFileName = "";

        if (ArrayUtils.contains(flags, "-c") ^ ArrayUtils.contains(flags, "-d")) {
            if (ArrayUtils.contains(flags, "-c")) {
                outputFileName = inputFileName + ".teemuzip";
            } else if (inputFileName.substring(inputFileName.length() - 9).equals(".teemuzip")) {
                outputFileName = inputFileName.substring(0, inputFileName.length() - 9);
            } else {
                printInstructionsAndQuit();
            }
        } else {
            printInstructionsAndQuit();
        }

        input = new File(inputFileName);
        output = new File(outputFileName);
    }

    private static void decompress(String[] args) throws IOException {
        HuffmanDecoding.decode(inputFileName, temp.getName());

        MoveToFront.reverseTransform(temp.getName(), temp2.getName());

        BurrowsWheeler.inverseTransform(temp2.getName(), outputFileName);
    }

    private static void compress(String[] args) throws IOException {
        BurrowsWheeler.transform(inputFileName, temp2.getName());

        MoveToFront.transform(temp2.getName(), temp.getName());

        HuffmanEncoding.encode(temp.getName(), outputFileName);
    }

    private static void compressWithPrinting(String[] args) throws IOException {
        System.out.println("Compression started.");

        System.out.print("Phase 1/3: Burrows-Wheeler transform started ...");
        long BWTStartTime = System.nanoTime();
        BurrowsWheeler.transform(inputFileName, temp2.getName());
        System.out.println("\t\tFinished. Time: " + String.format("%.3f", (System.nanoTime() - BWTStartTime) * 1.0e-9) + " sec.");

        System.out.print("Phase 2/3: Move to front transformation started ...");
        long mtfStartTime = System.nanoTime();
        MoveToFront.transform(temp2.getName(), temp.getName());
        System.out.println("\t\tFinished. Time: " + String.format("%.3f", (System.nanoTime() - mtfStartTime) * 1.0e-9) + " sec.");

        System.out.print("Phase 3/3: Huffman encoding started ...");
        long encodingStartTime = System.nanoTime();
        HuffmanEncoding.encode(temp.getName(), outputFileName);
        System.out.println("\t\t\t\tFinished. Time: " + String.format("%.3f", (System.nanoTime() - encodingStartTime) * 1.0e-9) + " sec.");

        long finishTime = System.nanoTime();
        System.out.println("Compression finished. Total time: " + (finishTime - startTime) * 1.0e-9 + " sec.");
        System.out.println("File size reduced from " + input.length() + " bytes to " + output.length() + " bytes (" + String.format("%.1f", 100.0 * output.length() / input.length()) + "% of original size).");
    }

    private static void decompressWithPrinting(String[] args) throws IOException {
        System.out.println("Decompression started.");

        System.out.print("Phase 1/3: Huffman decoding started ...");
        long decodingStartTime = System.nanoTime();
        HuffmanDecoding.decode(inputFileName, temp.getName());
        System.out.println("\t\t\t\tFinished. Time: " + String.format("%.3f", (System.nanoTime() - decodingStartTime) * 1.0e-9) + " sec.");

        System.out.print("Phase 2/3: Move to front inverse transform started ...");
        long mtfStartTime = System.nanoTime();
        MoveToFront.reverseTransform(temp.getName(), temp2.getName());
        System.out.println("\t\tFinished. Time: " + String.format("%.3f", (System.nanoTime() - mtfStartTime) * 1.0e-9) + " sec.");

        System.out.print("Phase 3/3: Burrows-Wheeler inverse transform started ....");
        long inverseBWTStartTime = System.nanoTime();
        BurrowsWheeler.inverseTransform(temp2.getName(), outputFileName);
        System.out.println("\tFinished. Time: " + String.format("%.3f", (System.nanoTime() - inverseBWTStartTime) * 1.0e-9) + " sec.");

        long finishTime = System.nanoTime();
        System.out.println("Decompression finished. Total time: " + (finishTime - startTime) * 1.0e-9 + " sec.");
    }

    private static void printInstructionsAndQuit() {
        System.out.println("Incorrect input arguments. Use:\n\n"
                + "> java -jar tiralabra.jar [options] filename \n\n"
                + "Available options:\n"
                + "-c: \t compress\n"
                + "-d: \t decompress (filename must end with .teemuzip)\n"
                + "-p: \t print progress information\n"
                + "IMPORTANT: either option -c or -d MUST always be given.");
        System.exit(0);
    }
}
