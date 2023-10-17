public class HuffmanTree {

    public String compressedMsg;
    public String decompressedMsg;
    private String[] codeTable = new String[28];
    private BinaryTree tree;


    public void compress(String input) {
        //Adds values to queue
        PriorityQueue sortedQueue = getFrequency(input);
        //Sorts Tress in Queue and returns Huffman Tree
        sortedQueue = getHuffmanTree(sortedQueue);
        //Stores Main Tree from sorted queue
        tree = sortedQueue.peek();
        Node root = tree.root;

        //Creates Code Table
        codeTable = createCodeTable(root);
        //Compress Message
        compressedMsg = compressMessage(input);
    }

    public void decompress(){
        decompressedMsg = decompressMessage(tree.root, compressedMsg);
    }
    //Gets frequency of letters in input
    private PriorityQueue getFrequency(String input){
        //Take a letter, store it, check to see if it exists, if not then count frequency
        //Creates queue size of current input
        PriorityQueue queue = new PriorityQueue(input.length());
        //Convert to uppercase
        input = input.toUpperCase();
        //Traverses Input by each letter
        for(int i =0; i < input.length(); i++){
            char currentLetter = input.charAt(i);
            int frequency = 0;
            //Does a secondary traversal through the remaining values
            for(int j = 0; j < input.length(); j++){
                if (j >= i) { //Checks remaining letters
                    //If there is a matching letter, increase frequency
                    if (input.charAt(j) == currentLetter)
                        frequency++;
                }
                else {
                    //Stops checking if a letter has already been counted
                    if (input.charAt(j) == currentLetter)
                        break;
                }
            }
            if (frequency >= 1){ //If there is at least one occurrence
                //Create a new tree
                BinaryTree tree = new BinaryTree();
                //Store Frequency and Letter in a node
                Node character = new Node(frequency, currentLetter);
                //Insert the node as the root of the tree
                tree.insert(character);
                //Insert tree into the queue
                queue.insert(tree);
            }
        }
        //returns the complete queue
        return queue;
    }
    //Sorts the Trees in the Queue to get Huffman Tree
    private PriorityQueue getHuffmanTree(PriorityQueue queue){
        //While there is more than one entry in queue
        while(queue.size > 1) {
            //Pop queue to give minimum value
            BinaryTree leftTree = queue.remove();
            Node leftChild = leftTree.root;
            //Pop queue to give second minimum value
            BinaryTree rightTree = queue.remove();
            Node rightChild = rightTree.root;
            //Get sum of both nodes
            int sum = leftChild.id + rightChild.id;
            //Create a new root node with no character value
            Node root = new Node(sum);
            //Create a new tree
            BinaryTree currentTree = new BinaryTree();
            //Store Sum Node as root
            currentTree.root = root;
            //Store left child
            currentTree.root.leftChild = leftChild;
            //Store right child
            currentTree.root.rightChild = rightChild;
            //re-insert into queue
            queue.insert(currentTree);
        }
        //Returns Sorted Queue with Huffman Tree at index 0
        return queue;
    }
    //Fills a table with Alphabetic values
    private String[] fillTable(String[] table){
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
    //Purges a table of any non encoded values
    private String[] purgeTable(String[] table) {
        for(int i = 0; i < table.length; i++){
            if (table[i].charAt(0) != '0' && table[i].charAt(0) != '1')
                table[i] = null;
        }

        return table;
    }
    //Creates the code table for reference
    private String[] createCodeTable(Node root){
        //Fill Code Table with values for reference
        String[] table = new String[28];
        table = fillTable(table);
        //Encode values using Pre-Order Traversal
        encodePreOrder(root, table);
        //Purges Code Table of any non encoded values
        table = purgeTable(table);

        return table;
    }
    //Encodes Letters Using Pre-Order Traversal
    private void encodePreOrder(Node root, String[] table){
        //Uses a string builder for encoded values
        StringBuilder code = new StringBuilder();
        if (root == null)
            return;
        //Left Traversal
        code.append("0");
        traverseNodes(root.leftChild, root.rightChild != null, code, table);
        //Right Traversal
        code.append("1");
        traverseNodes(root.rightChild, false, code, table);
    }
    //Traverses Nodes Recursively
    private void traverseNodes(Node node, boolean hasRightSibling, StringBuilder code, String[] table){
        if (node == null)
            return;
        //Encodes Value when at a leaf node
        encodeNode(node, code, table);
        //Traverses Left
        if (node.leftChild != null || node.rightChild != null)
            code.append("0"); //If not leaf node add 0
        traverseNodes(node.leftChild, node.rightChild!= null, code, table);
        //Traverses Right
        if (node.leftChild != null || node.rightChild != null)
            code.append("1"); //If not a leaf node add 1
        traverseNodes(node.rightChild, false, code, table);
        //Retain current encoding pathway
        code.deleteCharAt(code.length()-1);
    }
    //Stores Encoded Value in Code Table
    private void encodeNode(Node node, StringBuilder code, String[] table){
        if (node.leftChild == null && node.rightChild == null){
            //Is Leaf node
            int i = 0;
            while (node.data != table[i].charAt(0)){
                i++; //Sort Through Table until letter is found
            }
            //Replace letter with encoded value
            table[i] = code.toString();
        }
    }

    //Compresses input
    private String compressMessage(String input){
        //Covert To Uppercase
        input = input.toUpperCase();
        //Uses String builder for encoded Message
        StringBuilder encodedMessage = new StringBuilder();
        //Creates a Reference Table for characters
        String[] ref = new String[codeTable.length];
        //Fills table
        ref = fillTable(ref);
        //Goes through entire user input
        for(int i = 0; i < input.length(); i++){
            //Gets Current Letter
            char currentLetter = input.charAt(i);
            //Searches reference table for matching letter
            for(int j = 0; j < ref.length; j++){
                if (String.valueOf(currentLetter).equals(ref[j])){
                    //find corresponding index in code table and adds to encoded message
                    encodedMessage.append(codeTable[j]);
                }
            }
        }
        //returns encoded message
        return encodedMessage.toString();
    }
    //Decompresses Message
    private String decompressMessage(Node root, String input){
        //Gets Root Node
        Node current = root;
        //Uses String Builder for Decoded Message
        StringBuilder decodedMessage = new StringBuilder();
        //Goes through compressed message
        for(int i=0; i < input.length(); i++) {
            //Gets current direction
            char direction = input.charAt(i);
            if (direction == '0'){ //Go Left
                //Moves down one node to the left
                current = current.leftChild;
                //If leaf node
                if (current.leftChild == null && current.rightChild == null) {
                    //Add letter to decoded message
                    decodedMessage.append(current.data);
                    //Restart at root node
                    current = root;
                }
            }
            else if (direction == '1'){ //Go Right
                //Moves down one node to the right
                current = current.rightChild;
                //If leaf node
                if (current.leftChild == null && current.rightChild == null) {
                    //Add letter to decoded message
                    decodedMessage.append(current.data);
                    //Restart at root node
                    current = root;
                }
            }
        }

        return decodedMessage.toString();

    }
    //Prints Queue (FOR DEBUGGING)
    private void printQueue(PriorityQueue queue) {
        while (!queue.isEmpty()) {
            BinaryTree current = queue.remove();
            System.out.print("\nCharacter: " + current.root.data + "\nFrequency: " + current.root.id + "\n");
        }
    }
    //Prints Code Table (FOR DEBUGGING)
    private void printCodeTable(){
        System.out.println("Code Table: ");
        for(int i = 0; i < codeTable.length; i++){
            if (codeTable[i] != null)
                System.out.print(codeTable[i] + " ");
        }
    }

}
