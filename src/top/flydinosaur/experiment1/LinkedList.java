package top.flydinosaur.experiment1;

public interface LinkedList<T> {
    boolean isEmpty();
    int length();
    T get(int index);
    boolean set(int index, T x);
    void add(int index, T x);
    void append(T x);
    T remove(int index);
    void reverse();
    void clear();
}
