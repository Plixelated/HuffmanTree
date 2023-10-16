public class PriorityQueueTest {
    public static void main(String[] args){
        PriorityQueue pq = new PriorityQueue(10);

        //pq.insert(40);

        while(!pq.isEmpty()){
            System.out.println(pq.remove());
        }
    }
}
