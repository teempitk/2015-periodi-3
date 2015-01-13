
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuffmanTree{

    private HuffmanNode root;
    private final String[] codewords;

    public HuffmanTree(int[] frequencyTable) {
        List<HuffmanNode> initialNodes = new ArrayList<>();
        for(int i=0; i<128; i++){
            HuffmanNode newnode = new HuffmanNode((char)i,frequencyTable[i],null,null);
            initialNodes.add(newnode);
        }
        
        codewords = new String[128];
        generateTree(initialNodes);
        findCodewords(root,"");
    }

    private void generateTree(List<HuffmanNode> nodes) {
        Collections.sort(nodes);
        while (nodes.get(0).getFrequency() == 0) {
            nodes.remove(0);
        }
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            HuffmanNode left = nodes.get(0);
            HuffmanNode right = nodes.get(1);
            nodes.remove(0);
            nodes.remove(0);
            root = new HuffmanNode(' ',left.getFrequency()+right.getFrequency(),left, right);
            nodes.add(root);
        }
    }
    private void findCodewords(HuffmanNode node, String prefix){
        if (node.getLeft()!=null){
            findCodewords(node.getLeft(),prefix+"0");
            findCodewords(node.getRight(),prefix+"1");
        }
        else codewords[node.getSymbol()] = prefix;
    }
    
    public String[] getCodewords(){
        return codewords;
    }
}
