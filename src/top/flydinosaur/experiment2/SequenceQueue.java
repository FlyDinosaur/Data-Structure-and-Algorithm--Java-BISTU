package top.flydinosaur.experiment2;

public class SequenceQueue<T> implements Queue<T> {
    private Object[] values;
    private int head;
    private int tail;

    public SequenceQueue() {
        values = new Object[33];
        head = 0;
        tail = 0;
    }

    public SequenceQueue(int capacity) {
        //留一个空位判满
        values = new Object[capacity + 1];
        head = 0;
        tail = 0;
    }


    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public T enqueue(T item) {
        if (item == null) {
            return null;
        }

        if ((tail + 1) % values.length == head) {
            Object[] newValues = new Object[values.length * 2];
            int newIndex = 0;
            for (int i = head; i != tail; i = (i + 1) % values.length) {
                newValues[newIndex] = values[i];
                newIndex++;
            }
            head = 0;
            tail = newIndex;
            values = newValues;
        }

        values[tail] = item;
        tail = (tail + 1) % values.length;
        return item;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T item = (T) values[head];
        head = (head + 1) % values.length;
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        T item = (T) values[head];
        return item;
    }

    @Override
    public String toString() {
        String temp = "";
        temp += "Queue:[";
        for (int i = head; i != tail; i = (i + 1) % values.length) {
            temp += (T)values[i].toString();
            if ((i + 1) % values.length != tail) {
                temp += ", ";
            }
        }
        temp += "]";
        return temp;
    }

    public static void main(String[] args) {
        SequenceQueue<Integer> queue = new SequenceQueue<Integer>(5);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        System.out.println(queue);
        queue.enqueue(5);
        System.out.println(queue);
        queue.enqueue(6);
        queue.enqueue(7);
        System.out.println(queue);
        System.out.println("dequeue:" + queue.dequeue());
        System.out.println(queue);

    }
}
