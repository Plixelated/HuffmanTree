import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        //Run Program
        run();
    }

    public static void run(){
        //Get User Input
        System.out.println("Please Type A Sentence \n(Press Enter When Complete)");
        Scanner scnr = new Scanner(System.in);
        String input = scnr.nextLine();
        System.out.println("Input: " + input);
        //Create Huffman Tree
        HuffmanTree hTree = new HuffmanTree();
        //Compresses Input
        hTree.compress(input);
        //Prints out Compressed Message
        System.out.println("Compressed Message: " + hTree.compressedMsg);
        //Decompress Message
        hTree.decompress();
        //Prints out Decompressed Message
        System.out.println("Decoded Message: " + hTree.decompressedMsg);
    }
}
