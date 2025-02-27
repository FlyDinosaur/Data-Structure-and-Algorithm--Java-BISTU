package top.flydinosaur.experiment2;

public class SequenceStack<T> implements Stack<T> {

    private Object[] values;
    private int top;

    public SequenceStack(int capacity) {
        values = new Object[capacity];
        top = 0;
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    public int size() {
        return top;
    }

    @Override
    public void push(T item) {
        if (item == null){
            return;
        }
        if (top == values.length){
            Object[] newSequenceStack = new Object[values.length * 2];
            for (int i = 0; i < values.length; i++){
                newSequenceStack[i] = values[i];
            }
            values = newSequenceStack;
        }
        values[top] = item;
        top++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T value = (T) values[top - 1];
        top--;
        return value;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        T value = (T) values[top - 1];
        return value;
    }

    @Override
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        if (index < 0 || index >= top) {
            return null;
        }
        return (T)values[top - 1 - index];
    }

    @Override
    public boolean set(int index, T value) {
        if (isEmpty()) {
            return false;
        }
        if (index < 0 || index >= top) {
            return false;
        }
        values[top - 1 - index] = value;
        return true;
    }

    @Override
    public String toString() {
        String temp = "";
        temp += "Stack:[";
        for (int i = 0; i < top; i++) {
            temp += (T)values[i].toString();
            if (i != top - 1) {
                temp += ", ";
            }
        }
        temp += "]";
        return temp;
    }

    public static void main(String[] args) {
        SequenceStack<Integer> stack = new SequenceStack<Integer>(10);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack);
        System.out.println("pop:" + stack.pop());
        System.out.println(stack);
        System.out.println("peek:" + stack.peek());
        System.out.println("stack[0]:" + stack.get(0));
        stack.set(0, 4);
        System.out.println(stack);
    }
}
