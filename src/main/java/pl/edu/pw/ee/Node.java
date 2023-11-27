package pl.edu.pw.ee;

public class Node implements Comparable<Node> {

    private int frequency;
    private Character sign = null;
    private Node leftChild;
    private Node rightChild;
    

    public Node(char sign, int frequency) {
        this.sign = sign;
        this.frequency = frequency;

    }


    public Node( Node n1, Node n2){
        this.frequency = n1.frequency + n2.frequency;
        this.leftChild = n1;
        this.rightChild = n2;
    }

    @Override
    public int compareTo(Node node) {
        if (frequency > node.frequency) {
            return 1;
        } else if (frequency == node.frequency) {
            if(!this.isEmpty() && !node.isEmpty()){
                return sign.compareTo(node.getSign());
            }else if (this.isEmpty() && node.isEmpty()){
                return 0;
            }else {
                return isEmpty() ? 1 : -1;
            }
        } else {
            return -1;
        }
    }

    public int getFreguency() {
        return frequency;
    }

    public Character getSign() {
        return this.sign;
    }

    public Node getLeft() {
        return leftChild;
    }

    public void setLeft(Node leftNode) {
        leftChild = leftNode;
    }

    public Node getRight() {
        return rightChild;
    }

    public void setRight(Node rightNode) {
        rightChild = rightNode;
    }

    public boolean isEmpty(){
        return sign == null;
    }

}
