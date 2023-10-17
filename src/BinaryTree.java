public class BinaryTree {
    //Binary Tree To Store Key and Data
    Node root; //Root Node

    //Inserts node into tree
    public void insert(Node newNode) {
        if(root == null){
            root = newNode;
            return;
        }
        Node current = root;
        while (current != null){
            if (newNode.data == current.data && current.data != '\u0000')
                //no duplicates allowed
                throw new ArrayIndexOutOfBoundsException();
            if (newNode.id < current.id){
                if (current.leftChild == null){
                    current.leftChild = newNode;
                    break;
                } else {
                    current = current.leftChild;
                }
            }
            else{
                if (current.rightChild == null){
                    current.rightChild = newNode;
                    break;
                } else {
                    current = current.rightChild;
                }
            }


        }
    }

}