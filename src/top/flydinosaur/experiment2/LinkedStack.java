package top.flydinosaur.experiment2;

public class LinkedStack<T> implements Stack<T> {

    private Node<T> top;

    public LinkedStack() {
        top = null;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public int size() {
        int length = 0;
        Node<T> current = top;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }

    @Override
    public void push(T item) {
        if (item == null) {
            return;
        }
        top = new Node<T>(item, top);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T temp = top.data;
        top = top.next;
        return temp;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return top.data;
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            return null;
        }
        Node<T> current = top;
        for (int i = 0; i < index; i++) {
            if (current == null) {
                return null;
            }
            current = current.next;
        }
        if (current == null) {
            return null;
        }
        return current.data;
    }

    @Override
    public boolean set(int index, T item) {
        if (index < 0) {
            return false;
        }
        Node<T> current = top;
        for (int i = 0; i < index; i++) {
            if (current == null) {
                return false;
            }
            current = current.next;
        }
        if (current == null) {
            return false;
        }
        current.data = item;
        return true;
    }

    @Override
    public String toString() {
        String temp = "Stack:[";
        if (!isEmpty()) {
            temp += toStringRecursive(top);
        }
        temp += "]";
        return temp;
    }

    private String toStringRecursive(Node<T> node) {
        if (node.next == null) {
            return node.data.toString();
        }
        return toStringRecursive(node.next) + ", " + node.data;
    }

    class Node<T> {
        T data;
        Node<T> next;

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
        LinkedStack<Integer> stack = new LinkedStack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack);
        System.out.println("pop:" + stack.pop());
        System.out.println(stack);
        System.out.println("peek:" + stack.peek());
        System.out.println(stack);
        stack.set(0, 10);
        System.out.println(stack);
    }
}
