package top.flydinosaur.experiment1;

public class HeadSequenceLinkedList<T> implements LinkedList<T> {
    protected Node<T> head;

    public HeadSequenceLinkedList() {
        this.head = new Node<T>();
    }

    @Override
    public boolean isEmpty() {
        return this.head.next == null;
    }

    @Override
    public int length() {
        int length = 0;
        Node<T> p = this.head.next;
        while (p != null) {
            length++;
            p = p.next;
        }
        return length;
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            return null;
        }
        Node<T> p = this.head.next;
        for (int i = 0; i < index; i++) {
            if (p == null) {
                return null;
            }
            p = p.next;
        }
        return p == null ? null : p.value;
    }

    @Override
    public boolean set(int index, T x) {
        if (x == null || index < 0) {
            return false;
        }

        Node<T> p = this.head.next;
        for (int i = 0; i < index; i++) {
            if (p == null) {
                return false;
            }
            p = p.next;
        }
        //防止道链表尾部指向下一个为空
        if (p == null) {
            return false;
        }
        p.value = x;
        return true;
    }

    @Override
    public void add(int index, T x) {
        if (x == null) {
            return;
        }
        Node<T> p = this.head;
        for (int i = 0; i < index; i++) {
            if (p.next == null) {
                p.next = new Node<T>(x, null);
                return;
            }
            p = p.next;
        }
        Node<T> old = p.next;
        p.next = new Node<T>(x, old);
    }

    @Override
    public void append(T x) {
        if (x == null) {
            return;
        }
        Node<T> p = head;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new Node<T>(x);
    }

    @Override
    public T remove(int index) {
        if (index >= 0) {
            Node<T> p = this.head;
            for (int i = 0; p.next != null && i < index; i++) {
                p = p.next;
            }
            if (p.next != null) {
                T old = (T) p.next.value;
                p.next = p.next.next;
                return old;
            }
        }

        return null;
    }


    @Override
    public void clear() {
        this.head.next = null;
    }

    @Override
    public void reverse() {
        Node<T> pre = null;
        Node<T> current = head.next;
        Node<T> next = null;
        while (current != null) {
            next = current.next;
            current.next = pre;
            pre = current;
            current = next;
        }
        head.next = pre;
    }

    @Override
    public String toString() {
        String temp = "";
        temp += "LinkedList:[";
        Node<T> p = head.next;
        while (p != null) {
            temp += p.value;
            if (p.next != null) {
                temp += ", ";
            }
            p = p.next;
        }
        temp += "]";
        return temp;
    }

    public static void main(String[] args) {
        HeadSequenceLinkedList<Integer> list = new HeadSequenceLinkedList<Integer>();
        list.append(1);
        list.append(2);
        System.out.println(list);
        list.add(1, 9);
        System.out.println(list);
        list.reverse();
        System.out.println(list);
        list.remove(0);
        System.out.println(list);
    }
}

class Node<T> {
    T value;
    Node<T> next;

    public Node() {
        this.value = null;
        this.next = null;
    }

    public Node(T value) {
        this.value = value;
        this.next = null;
    }

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }
}