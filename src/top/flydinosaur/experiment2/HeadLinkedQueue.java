package top.flydinosaur.experiment2;

public class HeadLinkedQueue<T> implements Queue<T> {

    private Node<T> head;
    private Node<T> tail;

    public HeadLinkedQueue() {
        head = new Node<>(null); // 虚拟头节点
        tail = head;
    }

    @Override
    public boolean isEmpty() {
        return head.next == null;
    }

        @Override
        public T enqueue(T item) {
            if (item == null) {
                return null;
            }
            Node<T> node = new Node<>(item);
            tail.next = node;
            tail = node;
            return node.data;
        }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        Node<T> node = head.next;
        head.next = node.next;
        if (head.next == null) {
            tail = head; //此时head = tail, tail = head,队列为空
        }
        return node.data;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return head.next.data;
    }

    @Override
    public String toString() {
        String temp = "Queue:[";
        Node<T> node = head.next;
        while (node != null) {
            temp += node.data.toString();
            if (node.next != null) {
                temp += ", ";
            }
            node = node.next;
        }
        temp += "]";
        return temp;
    }

    class Node<T> {
        T data;
        Node<T> next;

        public Node() {
            data = null;
            next = null;
        }

        public Node(T data) {
            this.data = data;
            this.next = null;
        }

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        HeadLinkedQueue<Integer> queue = new HeadLinkedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        System.out.println(queue);
        System.out.println("dequeue:" + queue.dequeue());
        System.out.println(queue);
        System.out.println("peek:" + queue.peek());
        System.out.println(queue);
    }
}
