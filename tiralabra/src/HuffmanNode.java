
public class HuffmanNode extends Symbol{

    private final HuffmanNode leftChild;
    private final HuffmanNode rightChild;

    public HuffmanNode(char s, int freq, HuffmanNode left, HuffmanNode right) {
        super(s, freq);
        this.leftChild = left;
        this.rightChild = right;
    }
    
    public HuffmanNode getLeft(){
        return leftChild;
    }
    public HuffmanNode getRight(){
        return rightChild;
    }


}
