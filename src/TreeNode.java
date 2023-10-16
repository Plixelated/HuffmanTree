public class TreeNode { //Self Referencing class
    int id;
    char data;
    TreeNode leftChild;
    TreeNode rightChild;

    public TreeNode(int id, char data) {
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