package Huffman;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        args = new String[3];
        args[0]="c";
        args[1]="sampleFiles/20.jpg";
        args[2]="sampleFiles/pakattu";
        if(args.length!=3){
            System.out.println("Incorrect input arguments. To compress, use:\n\n"
                    + "> java Main c FileToCompress NameOfCompressedFile \n\n"
                    + "and to decompress:\n\n"
                    + "> java Main d NameOfCompressedFile NameOfDecompressedFile");
            System.exit(0);
        }
        HuffmanEncoding.encode(args[1],args[2]);
    }
}
