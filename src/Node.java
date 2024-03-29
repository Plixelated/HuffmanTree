public class Node { //Self Referencing class
    int id;
    char data;
    Node leftChild;
    Node rightChild;

    public Node(int id) {
        this.id = id;
    }
    public Node(int id, char data) {
        this.id = id;
        this.data = data;
    }

    public int getKey() {
        return id;
    }

    public void display() {
        System.out.println("[" + id + ", " + data + "]");
    }
}