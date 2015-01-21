package Huffman;

public class Main {

    public static void main(String[] args) {
        if(args.length!=3){
            System.out.println("Incorrect input arguments. To compress, use:\n\n"
                    + "> java Main c FileToCompress NameOfCompressedFile \n\n"
                    + "and to decompress:\n\n"
                    + "> java Main d NameOfCompressedFile NameOfDecompressedFile");
            System.exit(0);
        }
        HuffmanEncoding.encode(args[0],"");
    }
}
