public class PriorityQueue {
    public BinaryTree[] queue;
    public int size = -1;
    public static int maxSize;
    //Constructor
    public PriorityQueue(int items){
        maxSize = items; //Sets max size
        queue = new BinaryTree[maxSize]; //Creates new queue at max size
        size = 0; //sets current queue size to 0
    }
    //Inserts into queue
    public void insert(BinaryTree item){
        if (size >= queue.length -1){
            throw new IndexOutOfBoundsException();
        }

        int i;

        //First Item:
        if (size == 0){
            queue[size++] = item;
        }
        else{
            for(i = size-1; i >= 0; i--){
                if (item.root.id > queue[i].root.id){
                    //If smaller
                    queue[i+1] = queue[i];
                    //push to end of queue
                }
                else
                    break;
            }

            queue[i + 1] = item;
            size++;
        }
    }
    //Removes Last Item From Queue
    public BinaryTree remove(){
        return queue[--size];
    }
    //Previews Last Item in Queue
    public BinaryTree peek(){
        return queue[size-1];
    }
    //Checks If Empty
    public boolean isEmpty(){
        return size == 0;
    }
    //Checks If Full
    public boolean isFull(){
        return size == maxSize;
    }

}
