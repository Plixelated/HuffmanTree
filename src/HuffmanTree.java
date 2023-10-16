import java.nio.channels.Pipe;
import java.util.Scanner;

public class HuffmanTree {
    public static BinarySearchTree tree;
    public static PriorityQueue queue;
    public static String input;

    public static void main(String[] args) {
        System.out.println("Please Type A Sentence \n(Press Enter When Complete)");
        Scanner scnr = new Scanner(System.in);
        input = scnr.nextLine();
        addToQueue();
        while(!queue.isEmpty()){
            TreeNode node = queue.remove();
            System.out.print("\nCharacter: " + node.data + "\nFrequency: " + node.id + "\n");
        }
    }

    public static void addToQueue(){
        //Take a letter, store it, check to see if it exists, if not then count frequency
        queue = new PriorityQueue(input.length());
        input = input.toLowerCase();
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
                TreeNode character = new TreeNode(frequency, currentLetter);
                queue.insert(character);
            }
        }
    }

}
