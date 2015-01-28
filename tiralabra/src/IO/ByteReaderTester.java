
package IO;

import java.io.File;
import java.io.IOException;


public class ByteReaderTester {
    public static void main(String[] args) throws IOException{
        File file = new File("sampleFiles/alice.txt");
        ByteReader reader = new ByteReader(file);
        int count=0;
        while(reader.hasNext() && count<20){
            System.out.println(reader.readByte());
            count++;
        }
    }
    
}
