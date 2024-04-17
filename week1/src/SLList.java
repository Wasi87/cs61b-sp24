public class SLList {
    
    private class IntNode {
        public int item;
        public IntNode next;
    
        public IntNode(int f, IntNode r) {
            item = f;
            next = r;
        }
    }

    // 第一個item 是在 sentinel.next
    private IntNode sentinel; // instance變數 
    private int size;

    // SLList 建構子
    public SLList(int x){
        sentinel = new IntNode(0, null); 
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    // empty SLList
    public SLList(){
        sentinel = new IntNode(0, null);
        size = 0;
    }

    public void addFirst(int x){
        size += 1;
        sentinel.next = new IntNode(x, sentinel.next);
    }

    public int getFirst(){
        return sentinel.next.item;
    }

    public void addLast(int x){
        size += 1;
        IntNode p = sentinel;

        while (p.next != null) {
            p = p.next;
        }

        p.next = new IntNode(x, null);
    }

    private static int size(IntNode p) {
        if (p.next ==null) {
            return 1;
        }
        return 1 + size(p.next);
    }

    public int size(){
        return size(first);
    }

    public static void main(String[] args) {
        SLList L = new SLList();
        L.addFirst(10);
        L.addFirst(5);
        L.addLast(20); // NullPointerException
        System.out.println(L.getFirst());
    }
}
