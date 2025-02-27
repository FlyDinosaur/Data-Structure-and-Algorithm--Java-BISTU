package top.flydinosaur.experiment2;

public interface Stack<T> {
    boolean isEmpty();
    int size();
    void push(T item);
    T pop();
    T peek();
    T get(int index);
    boolean set(int index, T item);
}
