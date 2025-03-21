package top.flydinosaur.experiment5;

import top.flydinosaur.experiment1.SequenceList;

public class LongInteger {

    private DoublyCircularLinkedList<Integer> number;
    // 标记是否为负数
    private boolean isNegative;

    public LongInteger(String s) {
        number = new DoublyCircularLinkedList<>();
        if (s.isEmpty()) throw new IllegalArgumentException();

        // 检查每个数的第一位是否带有负号，如果有，则实际有意义的数值从下表1开始，否则从下标0开始
        int start = 0;
        if (s.charAt(0) == '-') {
            isNegative = true;
            start = 1;
        } else {
            isNegative = false;
        }

        // 去除多余的“，”，只保留数据
        String numStr = s.substring(start).replaceAll(",", "");
        int len = numStr.length();

        // 每四位为一组保存至双向链表中
        for (int i = len; i > 0; i -= 4) {
            int begin = Math.max(i-4, 0);
            String part = numStr.substring(begin, i);
            int value = Integer.parseInt(part);
            number.add(value);
        }

        if (number.isEmpty()) number.add(0);
        number.removeLeadingZeros();
        if (isZero()) {
            isNegative = false;
        }
    }

    private LongInteger(DoublyCircularLinkedList<Integer> num, boolean isNeg) {
        this.number = num;
        this.isNegative = isNeg;
        number.removeLeadingZeros();
        if (isZero()) this.isNegative = false;
    }

    public boolean isZero() {
        return number.getSize() == 1 && number.getValues().get(0) == 0;
    }

    public LongInteger add(LongInteger other) {
        // 符号相同，绝对值相加并带入相同符号
        if (this.isNegative == other.isNegative) {
            return new LongInteger(
                    addAbsolute(this.number, other.number),
                    this.isNegative
            );
        } else {
            int cmpResult = compareAbsolute(other);
            // 两数相等，相减为0
            if (cmpResult == 0) return new LongInteger("0");
            // 被减数位数大于减数位数，结果符号为减数的符号，然后大数-小数每四位为一组相减
            else if (cmpResult > 0) {
                return new LongInteger(
                        subtractAbsolute(this.number, other.number),
                        this.isNegative
                );
            // 减数位数大于被减数位数，结果符号一定为减数的符号，然后大数-小数每四位为一组相减
            } else {
                return new LongInteger(
                        subtractAbsolute(other.number, this.number),
                        other.isNegative
                );
            }
        }

    }

    public LongInteger minus(LongInteger other) {
        if (other.isNegative) {
            other.isNegative = false;
        } else {
            other.isNegative = true;
        }
        return add(other);
    }

    private static DoublyCircularLinkedList<Integer> addAbsolute(
            DoublyCircularLinkedList<Integer> a,
            DoublyCircularLinkedList<Integer> b
    ) {
        SequenceList<Integer> aVals = a.getValues();
        SequenceList<Integer> bVals = b.getValues();
        DoublyCircularLinkedList<Integer> result = new DoublyCircularLinkedList<>();

        // 初始表示进位为0
        int carry = 0;
        int maxSize = Math.max(aVals.size(), bVals.size());
        // 按小往大相加，若每四位相加产生进位（大于9999），则通过取模只保留四位数，剩余的一位当作进位，并进行指针向后移动，如果该4位没有数据，但又有进位，则只保留进位（被加数+进位+加数）
        for (int i = 0; i < maxSize || carry != 0; i++) {
            int aVal = (i < aVals.size()) ? aVals.get(i) : 0;
            int bVal = (i < bVals.size()) ? bVals.get(i) : 0;
            int sum = aVal + bVal + carry;
            carry = sum / 10000;
            result.add(sum % 10000);
        }
        result.removeLeadingZeros();
        return result;
    }

    private static DoublyCircularLinkedList<Integer> subtractAbsolute(
            DoublyCircularLinkedList<Integer> larger,
            DoublyCircularLinkedList<Integer> smaller
    ) {
        SequenceList<Integer> lVals = larger.getValues();
        SequenceList<Integer> sVals = smaller.getValues();
        DoublyCircularLinkedList<Integer> result = new DoublyCircularLinkedList<>();

        // 表示借位
        int borrow = 0;
        // 有借位发生时，被减数先剪去进位数，再减去小的数，否则直接相减，若结果为负，表示需要进位，1进位代表10000，与刚才的结果相加即为计算结果
        for (int i = 0; i < lVals.size(); i++) {
            int lVal = lVals.get(i);
            int sVal = (i < sVals.size()) ? sVals.get(i) : 0;
            int sub = lVal - borrow - sVal;
            borrow = sub < 0 ? 1 : 0;
            sub += borrow * 10000;
            result.add(sub);
        }
        result.removeLeadingZeros();
        return result;
    }

    private int compareAbsolute(LongInteger other) {
        SequenceList<Integer> thisVals = this.number.getValues();
        SequenceList<Integer> otherVals = other.number.getValues();

        if (thisVals.size() != otherVals.size()) {
            return Integer.compare(thisVals.size(), otherVals.size());
        }
        for (int i = thisVals.size()-1; i >= 0; i--) {
            int thisVal = thisVals.get(i);
            int otherVal = otherVals.get(i);
            if (thisVal != otherVal) {
                return Integer.compare(thisVal, otherVal);
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        if (isZero()) return "0";

        SequenceList<Integer> values = number.getValues();
        String temp = isNegative ? "-" : "";

        // 反转顺序处理高位到低位
        int highPart = values.get(values.size()-1);
        temp += highPart;

        for (int i = values.size()-2; i >= 0; i--) {
            String part = String.format("%04d", values.get(i));
            temp += "," + part;
        }
        return temp;
    }


    public static void main(String[] args) {

        LongInteger a = new LongInteger("0");
        LongInteger b = new LongInteger("0");
        System.out.println(a.minus(b));

        LongInteger c = new LongInteger("2345,6789");
        LongInteger d = new LongInteger("-7654,3211");
        System.out.println(c.minus(d));

        LongInteger e = new LongInteger("1,0000,0000,0000");
        LongInteger f = new LongInteger("9999,9999");
        System.out.println(e.minus(f));

        LongInteger g = new LongInteger("1,0001,0001");
        LongInteger h = new LongInteger("1,0001,0001");
        System.out.println(g.minus(h));

        LongInteger i = new LongInteger("83,7732,1396,1101");
        LongInteger j = new LongInteger("24,3952,8498,0101");
        // 108，1684，9894，1202
        System.out.println(i.add(j));
    }
}
