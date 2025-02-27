package top.flydinosaur.experiment1;

public class SequenceList<T> implements LinearList<T> {

    protected Object[] values;
    protected int length;

    public SequenceList(int capacity) {
        values = new Object[capacity];
        length = 0;
    }

    @Override
    public void clear() {
        if (isEmpty()){
            return;
        } else {
            values = new Object[values.length];
            length = 0;
        }
    }


    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public int size() {
        return length;
    }


    @Override
    public T get(int index) {
        if (index < 0 || index >= length) {
            return null;
        }
        return (T) values[index];
    }

    @Override
    public void set(int index, T value) {
        if (value == null) {
            return;
        }
        if (index < 0 || index >= length) {
            return;
        }
        values[index] = value;
    }

    @Override
    public boolean add(int index, T value) {
        if (value == null){
            return false;
        }
        if (length == values.length) {
            Object[] newValues = new Object[values.length * 2];
            for (int i = 0; i < values.length; i++) {
                newValues[i] = values[i];
            }
            values = newValues;
        }
        if (index > length){
            index = length;
        }
        if (index < 0) {
            index = 0;
        }
        for (int i = length - 1; i >= index; i--) {
            values[i+1] = values[i];
        }
        values[index] = value;
        length++;
        return true;
    }

    public boolean append(T value) {
        if (value == null){
            return false;
        }
        if (length == values.length) {
            Object[] newValues = new Object[values.length * 2];
            for (int i = 0; i < values.length; i++) {
                newValues[i] = values[i];
            }
            values = newValues;
        }
        values[length] = value;
        length++;
        return true;
    }

    @Override
    public T remove(int index) {
        if (length != 0 && index >= 0 && index < length) {
            T tobeRemoved = (T) values[index];
            for (int i = index; i < length; i++) {
                values[i] = values[i+1];
            }
            values[length-1] = null;
            length--;
            return tobeRemoved;
        }
        return null;
    }

    @Override
    public T indexOf(T value) {
        for (int i = 0; i < length; i++) {
            if (value.equals(values[i])) {
                return (T) values[i];
            }
        }
        return null;
    }

    public int getIndex(T value) {
        for (int i = 0; i < length; i++) {
            if (value.equals(values[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String temp = "";
        temp += "SequenceList:[";
        for (int i = 0; i < length; i++) {
            temp += (T) values[i].toString();
            if (i < length - 1) {
                temp += ", ";
            }
        }
        temp += "]";
        return temp;
    }

    public boolean hasNext(int index) {
        return index < length;
    }

    public static void main(String[] args) {
        SequenceList<Integer> list = new SequenceList<Integer>(2);
        System.out.println(list.isEmpty());
        list.append(1);
        list.append(2);
        System.out.println(list.isEmpty());
        System.out.println(list);
        list.add(1, 10);
        System.out.println(list);
        list.remove(2);
        System.out.println(list);
        System.out.println(list.get(0));
        System.out.println(list.indexOf(1));
        list.clear();
        System.out.println(list);
    }
}
