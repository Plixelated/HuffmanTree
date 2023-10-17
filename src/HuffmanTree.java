import java.util.Scanner;

public class HuffmanTree {

    public static String encodedMsg;
    public static String decodedMsg;
    public static String[] codeTable = new String[28];

    public static void main(String[] args) {
        String input;

        System.out.println("Please Type A Sentence \n(Press Enter When Complete)");
        Scanner scnr = new Scanner(System.in);
        input = scnr.nextLine();

        //input = "\nSusie says it is easy";
        System.out.println("Input: " + input);
        PriorityQueue queue = new PriorityQueue(input.length());

        //Adds values to queue
        queue = addToQueue(queue, input);
        //Sorts Tress in Queue
        queue = sortTrees(queue);
        //Creates reference to tree in queue
        BinarySearchTree tree = queue.peek();

        //Creates Code Table
        codeTable = createCodeTable(tree.root);

        encodedMsg = encodeMessage(input);
        decodedMsg = decodeTable(tree.root, encodedMsg);

        System.out.println("Encoded Message: " + encodedMsg);
        System.out.println("Decoded Message: " + decodedMsg);
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

    private static String[] fillTable(String[] table){
        //Fills a table with alphabetical values
        for (int i = 0; i < table.length; i++){
            if (i < table.length-2) {
                int charCode = 65 + i;
                table[i] = String.valueOf((char)charCode);
            }
            else{
                if (i == table.length-2) {
                    table[i] = " ";
                }
                else
                    table[i] = "\n";
            }
        }

        return table;
    }

    private static String[] purgeTable(String[] table) {
        for(int i = 0; i < table.length; i++){
            if (table[i].charAt(0) != '0' && table[i].charAt(0) != '1')
                table[i] = null;
        }

        return table;
    }

    private static String[] createCodeTable(Node root){
        codeTable = fillTable(codeTable);
        traversePreOrder(root);
        codeTable = purgeTable(codeTable);

        return codeTable;
    }

    private static String encodeMessage(String input){
        input = input.toUpperCase();
        StringBuilder encodedMessage = new StringBuilder();
        char[] ref = new char[codeTable.length];
        for (int i = 0; i < ref.length; i++){
            if (i < ref.length-2) {
                int charCode = 65 + i;
                ref[i] = (char) charCode;
            }
            else{
                if (i == ref.length-2) {
                    ref[i] = ' ';
                }
                else
                    ref[i] = '\n';
            }
        }
        for(int i = 0; i < input.length(); i++){
            char currentLetter = input.charAt(i);
            for(int j = 0; j < ref.length; j++){
                if (currentLetter == ref[j]){
                    encodedMessage.append(codeTable[j]);
                }
            }
        }

        return encodedMessage.toString();
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

    private static String decodeTable(Node root, String input){
        Node current = root;
        StringBuilder decodedMessage = new StringBuilder();

        for(int i=0; i < input.length(); i++) {
            char currentValue = input.charAt(i);
            if (currentValue == '0'){
                current = current.leftChild;
                if (current.leftChild == null && current.rightChild == null) {
                    decodedMessage.append(current.data);
                    current = root;
                }
            }
            else if (currentValue == '1'){
                current = current.rightChild;
                if (current.leftChild == null && current.rightChild == null) {
                    decodedMessage.append(current.data);
                    current = root;
                }
            }

        }

        return decodedMessage.toString();

    }

    private static void printQueue(PriorityQueue queue) {
        while (!queue.isEmpty()) {
            BinarySearchTree current = queue.remove();
            System.out.print("\nCharacter: " + current.root.data + "\nFrequency: " + current.root.id + "\n");
        }
    }

    private static void printCodeTable(){
        System.out.println("Code Table: ");
        for(int i = 0; i < codeTable.length; i++){
            if (codeTable[i] != null)
                System.out.print(codeTable[i] + " ");
        }
    }

}
