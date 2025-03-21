package top.flydinosaur.experiment5;

import top.flydinosaur.experiment1.SequenceList;

public class DoublyCircularLinkedList<T> {

    private Node<T> head;
    private int size; // 有几个节点，用于后续比较整数大小

    class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        Node(T value) {
            this.value = value;
            this.prev = this;
            this.next = this;
        }

        Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    public DoublyCircularLinkedList() {
        head = null;
        size = 0;
    }

    public void add(T value) {
        if (value == null) {
            return;
        }
        Node<T> newNode = new Node<>(value);
        // 如果为空表，则将节点头设置为当前数值
        if (head == null) {
            head = newNode;
        } else {
            // 第一个节点的前继是尾节点，尾节点的后继指向待插入的节点，待插入节点的后继为头节点，前继为之前的尾节点，头节点的前继指向新插入的节点
            Node<T> tail = head.prev;
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
        }
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    // 用顺序表暂存这个长整数
    public SequenceList<T> getValues() {
        SequenceList<T> values = new SequenceList<>(size);
        if (head == null) {
            return values;
        }
        Node<T> current = head;
        values.append(current.value);
        current = current.next;
        while (current != head) {
            values.append(current.value);
            current = current.next;
        }
        return values;
    }

    // 删除每四位前多余的0，方便后续计算进位
    public void removeLeadingZeros() {
        if (size <= 1) {
            return;
        }
        // 从后往前，低位到高位
        Node<T> current = head.prev;

        while (size > 1 && isZeroValue(current.value)) {
            Node<T> prevNode = current.prev;
            prevNode.next = head;
            head.prev = prevNode;
            current = prevNode;
            size--;
        }
    }

    private boolean isZeroValue(T value) {
        if (value instanceof Number) {
            return ((Number) value).intValue() == 0;
        }
        throw new UnsupportedOperationException("仅支持数值类型");
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        if (size == 1) {
            head = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            if (current == head) {
                head = current.next;
            }
        }
        size--;
        return current.value;
    }

    public void set(int index, T value) {
        if (index < 0 || index >= size) {
            return;
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.value = value;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "DoublyCircularLinkedList:[]";
        }
        String result = "DoublyCircularLinkedList:[";
        Node<T> current = head;
        do {
            result += current.value;
            if (current.next != head) {
                result += ", ";
            }
            current = current.next;
        } while (current != head);
        return result + "]";


    }


    public static void main(String[] args) {
        DoublyCircularLinkedList<Integer> list = new DoublyCircularLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);
        list.remove(1);
        System.out.println(list);
        list.set(0, 4);
        System.out.println(list);

    }
}