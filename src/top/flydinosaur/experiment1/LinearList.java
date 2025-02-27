package top.flydinosaur.experiment1;

public interface LinearList<T> {
    void clear();
    boolean isEmpty();
    int size();
    T get(int index);
    void set(int index, T value);
    boolean add(int index, T value);
    T remove(int index);
    T indexOf(T value);
}
