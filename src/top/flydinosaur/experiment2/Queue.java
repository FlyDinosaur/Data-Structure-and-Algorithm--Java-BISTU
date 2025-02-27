package top.flydinosaur.experiment2;

public interface Queue<T> {
    boolean isEmpty();
    T enqueue(T item);
    T dequeue();
    T peek();
}
