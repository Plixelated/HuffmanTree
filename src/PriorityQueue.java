public class PriorityQueue {
    public BinarySearchTree[] queue;
    public int size = -1;
    public static int maxSize;

    public PriorityQueue(int items){
        maxSize = items;
        queue = new BinarySearchTree[maxSize];
        size = 0;
    }

    public void insert(BinarySearchTree item){
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
                    //If larger
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

    public BinarySearchTree remove(){
        return queue[--size];
    }

    public BinarySearchTree peek(){
        return queue[size-1];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isFull(){
        return size == maxSize;
    }

}
