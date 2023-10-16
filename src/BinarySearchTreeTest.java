public class BinarySearchTreeTest {
    public static void main(String[] args) {

        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(30, 'c');
        bst.insert(10, 'a');
        bst.insert(20, 'b');
        bst.insert(40, 'd');
        bst.insert(50, 'e');

        TreeNode result = bst.find(50);

        System.out.println((char)result.data);
    }
}
