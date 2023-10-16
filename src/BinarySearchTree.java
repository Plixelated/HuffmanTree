public class BinarySearchTree {
   Node root;

    public Node find(int key) {
        Node current = root;
        while (current != null){
            if (key == current.id)
                return current;
            else if (key < current.id){
                current = current.leftChild;
            }
            else{
                current = current.rightChild;
            }
        }

        return null;
    }

    public void insert(int id, char data) {
        Node newNode = new Node(id, data);
        insert(newNode);
    }

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

    public boolean delete(int key) {
        //search for node to be deleted
        Node current = root;
        Node parent = null;
        boolean isLeftChild = false;

        while (current != null){
            if (key == current.id)
                break;

            parent = current;

            if (key < current.id){
                isLeftChild = true;
                current = current.leftChild;
            }
            else{
                isLeftChild = false;
                current = current.rightChild;
            }
        }

        if (current == null) //current will be null if tree is empty or key dne
            return false;

        //leaf node
        if (current.leftChild == null && current.rightChild == null){
            if (parent == null) {
                root = null;
            }
            else {
                if(isLeftChild)
                    parent.leftChild = null;
                else{
                    parent.rightChild = null;
                }
            }
        }
        //Non-leaf node
        else if (current.rightChild == null) { //LEFT
            if (parent == null) {
                root = current.leftChild;
            }
            else {
                if (isLeftChild){
                    parent.leftChild = current.leftChild;
                }
                else {
                    parent.rightChild = current.leftChild;
                }
            }
        } else if (current.leftChild == null){ //RIGHT
            if (parent == null)
                root = current.rightChild;
            else{
                if (isLeftChild){
                    parent.leftChild = current.rightChild;
                }
                else {
                    parent.rightChild = current.rightChild;
                }
            }
        }
        else{ //Both children present
            Node successor = findSuccessor(current);

            successor.leftChild = current.leftChild;
            if (successor != current.rightChild)
                successor.rightChild = current.rightChild;

            if (parent == null){
                root = successor;
            }
            else{
                if (isLeftChild)
                    parent.leftChild = successor;
                else{
                    parent.rightChild = successor;
                }
            }
        }


        return true;
    }

    private Node findSuccessor(Node delNode){
        Node successor = delNode.rightChild;
        Node successorParent = delNode;

        if (successor.leftChild == null)
            return successor;

        while(successor.leftChild != null){
            successorParent = successor;
            successor = successor.leftChild;
        }

        if (successor != delNode.rightChild){
            successorParent.leftChild = successor.rightChild;
        }
        return successor;
    }

    private String traversePreOrder(Node root) {
        if (root == null)
            return "";

        StringBuilder sb = new StringBuilder();
        sb.append(root.id);
        sb.append(": ");
        sb.append(root.data);

        String pointerRight = "---";
        String pointerLeft = "|--";

        traverseNodes(sb, "", pointerLeft, root.leftChild, root.rightChild != null);
        traverseNodes(sb, "", pointerRight, root.rightChild, false);

        return sb.toString();
    }

    private void traverseNodes(StringBuilder sb, String padding, String pointer,
                               Node node, boolean hasRightSibling) {

        if (node == null)
            return;

        sb.append("\n");
        sb.append(padding);
        sb.append(pointer);
        sb.append(node.id);
        sb.append(": ");
        sb.append(node.data);

        StringBuilder pb = new StringBuilder(padding);
        if (hasRightSibling)
            pb.append("|  ");
        else
            pb.append("   ");

        String p = pb.toString();
        String pntr = "|--";
        traverseNodes(sb, p, pntr, node.leftChild, node.rightChild != null);
        traverseNodes(sb, p, pntr, node.rightChild, false);
    }

    public Node getMinimum() {
        if (root == null)
            return null;

        Node current = root;
        while(current.leftChild != null)
            current = current.leftChild;

        return current;
    }

    public Node getMaximum() {
        if (root == null)
            return null;

        Node current = root;
        while(current.rightChild != null)
            current = current.rightChild;

        return current;
    }

    public void print() {
        String s = traversePreOrder(root);
        System.out.println(s);
    }
}