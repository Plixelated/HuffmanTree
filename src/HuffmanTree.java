import java.util.Scanner;

public class HuffmanTree {

    public static String[] codeTable = new String[28];

    public static void main(String[] args) {
        String input;

        //System.out.println("Please Type A Sentence \n(Press Enter When Complete)");
        //Scanner scnr = new Scanner(System.in);
        //input = scnr.nextLine();

        input = "\nSusie says it is easy";
        PriorityQueue queue = new PriorityQueue(input.length());
        queue = addToQueue(queue, input);
        queue = sortTrees(queue);
        encodeTable(queue);
        printCodeTable();
        decodeTable(queue);
    }

    public static PriorityQueue addToQueue(PriorityQueue queue, String input){
        //Take a letter, store it, check to see if it exists, if not then count frequency
        queue = new PriorityQueue(input.length());
        input = input.toUpperCase();
        for(int i =0; i < input.length(); i++){
            char currentLetter = input.charAt(i);
            int frequency = 0;
            for(int j = 0; j < input.length(); j++){
                if (j >= i) {
                    if (input.charAt(j) == currentLetter)
                        frequency++;
                }
                else {
                    if (input.charAt(j) == currentLetter)
                        break;
                }
            }
            if (frequency >= 1){
                BinarySearchTree tree = new BinarySearchTree();
                Node character = new Node(frequency, currentLetter);
                tree.insert(character);
                queue.insert(tree);
            }
        }

        return queue;
    }

    private static PriorityQueue sortTrees(PriorityQueue queue){
        while(queue.size > 1) {
            BinarySearchTree leftTree = queue.remove();
            Node leftChild = leftTree.root;

            BinarySearchTree rightTree = queue.remove();
            Node rightChild = rightTree.root;

            int sum = leftChild.id + rightChild.id;

            Node root = new Node(sum);

            BinarySearchTree currentTree = new BinarySearchTree();
            currentTree.root = root;
            currentTree.root.leftChild = leftChild;
            currentTree.root.rightChild = rightChild;

            queue.insert(currentTree);
        }

        return queue;
    }

    private static void setCodeTable(){
        //Fills code table
        for (int i = 0; i < codeTable.length; i++){
            if (i < codeTable.length-2) {
                int charCode = 65 + i;
                char letter = (char) charCode;
                codeTable[i] = String.valueOf(letter);
            }
            else{
                if (i == codeTable.length-2) {
                    codeTable[i] = " ";
                }
                else
                    codeTable[i] = "\n";
            }
        }
    }
    private static void encodeTable(PriorityQueue queue){
        setCodeTable();
        Node root = queue.peek().root;
        traversePreOrder(root);
    }

    private static void traversePreOrder(Node root){
        StringBuilder code = new StringBuilder();
        if (root == null)
            return;
        code.append("0");
        traverseNodes(root.leftChild, root.rightChild != null, code);
        code.append("1");
        traverseNodes(root.rightChild, false, code);
    }

    private static void traverseNodes(Node node, boolean hasRightSibling, StringBuilder code){
        if (node == null)
            return;
        encodeNode(node, code);
        //Traverse Left
        if (node.leftChild != null || node.rightChild != null)
            code.append("0");
        traverseNodes(node.leftChild, node.rightChild!= null, code);
        //Traverse Right
        if (node.leftChild != null || node.rightChild != null)
            code.append("1");
        traverseNodes(node.rightChild, false, code);
        code.deleteCharAt(code.length()-1);
    }

    private static void encodeNode(Node node, StringBuilder code){

        if (node.leftChild == null && node.rightChild == null){
            //Is Leaf node
            int i = 0;
            while (node.data != codeTable[i].charAt(0)){
                i++;
            }
            codeTable[i] = code.toString();
        }

    }

    private static void decodeTable(PriorityQueue queue){
        Node root = queue.peek().root;

    }

    private static void printQueue(PriorityQueue queue) {
        while (!queue.isEmpty()) {
            BinarySearchTree current = queue.remove();
            System.out.print("\nCharacter: " + current.root.data + "\nFrequency: " + current.root.id + "\n");
        }
    }

    private static void printCodeTable(){
        System.out.print("Encoded Message: ");
        for(int i = 0; i < codeTable.length; i++){
            if (codeTable[i].charAt(0) == '0' || codeTable[i].charAt(0) == '1')
                System.out.print(codeTable[i] + " ");
        }
    }

}
