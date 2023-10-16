public class PriorityQueue {
    public TreeNode[] queue;
    public int size = -1;
    public static int maxSize;

    public PriorityQueue(int items){
        maxSize = items;
        queue = new TreeNode[maxSize];
        size = 0;
    }

    public void insert(TreeNode item){
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
                if (item.id < queue[i].id){
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

    public TreeNode remove(){
        return queue[--size];
    }

    public TreeNode peek(){
        return queue[size-1];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isFull(){
        return size == maxSize;
    }

}
