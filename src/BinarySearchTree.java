public class BinarySearchTree {
   TreeNode root;

    public TreeNode find(int key) {
        TreeNode current = root;
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
        TreeNode newNode = new TreeNode(id, data);
        insert(newNode);
    }

    //Todo: Implement the Node insertion
    public void insert(TreeNode newNode) {
        if(root == null){
            root = newNode;
            return;
        }
        TreeNode current = root;
        while (current != null){
            if (newNode.id == current.id)
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

    //Todo: Implement - delete a Node given a key
    public boolean delete(int key) {
        //search for node to be deleted
        TreeNode current = root;
        TreeNode parent = null;
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
            TreeNode successor = findSuccessor(current);

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

    private TreeNode findSuccessor(TreeNode delNode){
        TreeNode successor = delNode.rightChild;
        TreeNode successorParent = delNode;

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

    public TreeNode getMinimum() {
        if (root == null)
            return null;

        TreeNode current = root;
        while(current.leftChild != null)
            current = current.leftChild;

        return current;
    }

    public TreeNode getMaximum() {
        if (root == null)
            return null;

        TreeNode current = root;
        while(current.rightChild != null)
            current = current.rightChild;

        return current;
    }
}