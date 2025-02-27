package top.flydinosaur.experiment1;

public class OneLetterPolynomial {
    private HeadSequenceLinkedList<Term> polynomial;

    public OneLetterPolynomial(HeadSequenceLinkedList<Term> polynomial) {
        this.polynomial = polynomial;
    }

    public OneLetterPolynomial(SequenceList<double []> input) {
        polynomial = new HeadSequenceLinkedList<>();
        for (int i = 0; i < input.length; i++){
            double[] tmp = input.get(i);
            addTerm(tmp[0], tmp[1]);
        }
    }

    // 对每个项节点，若不存在该指数项，则加入到表中，若存在，则进行系数相加合并，查找时，按照指数降序查找并插入元素
    private boolean addTerm(double coefficient, double exponent) {

        Term newTerm = new Term(coefficient, exponent);
        int index = 0;
        Node<Term> current = polynomial.head.next;
        while (current != null && current.value.exponent > exponent) {
            index++;
            current = current.next;
        }
        if (current != null && current.value.exponent == exponent) {
            current.value.coefficient += coefficient;
            return true;
        } else {
            polynomial.add(index, newTerm);
            return true;
        }
    }

    public boolean isEmpty() {
        return this.polynomial.isEmpty();
    }

    public OneLetterPolynomial addPolynomial(OneLetterPolynomial other) {
        //空+空=空，若只有一个空，则返回另一个不是空的
        if (this.isEmpty() && other.isEmpty()) {
            return null;
        } else if (this.isEmpty()) {
            return new OneLetterPolynomial(other.polynomial);
        } else if (other.isEmpty()) {
            return new OneLetterPolynomial(this.polynomial);
        }

        HeadSequenceLinkedList<Term> resultList = new HeadSequenceLinkedList<>();
        Node<Term> thisCurrent = this.polynomial.head.next;
        Node<Term> otherCurrent = other.polynomial.head.next;
        Node<Term> resultCurrent = resultList.head;

        //同时遍历两个列表，当两者有一个到结尾时，说明后面的不需要再相加，直接退出遍历
        while (thisCurrent != null && otherCurrent != null) {
            //指数大的作新节点，并继续查找下一个节点，遇到指数相同的则相加作新节点，添加到新多项式
            if (thisCurrent.value.exponent > otherCurrent.value.exponent) {
                resultCurrent.next = new Node<>(new Term(thisCurrent.value.coefficient, thisCurrent.value.exponent));
                thisCurrent = thisCurrent.next;
            } else if (thisCurrent.value.exponent < otherCurrent.value.exponent) {
                resultCurrent.next = new Node<>(new Term(otherCurrent.value.coefficient, otherCurrent.value.exponent));
                otherCurrent = otherCurrent.next;
            } else {
                double sumCoefficient = thisCurrent.value.coefficient + otherCurrent.value.coefficient;
                if (sumCoefficient != 0) {
                    resultCurrent.next = new Node<>(new Term(sumCoefficient, thisCurrent.value.exponent));
                }
                thisCurrent = thisCurrent.next;
                otherCurrent = otherCurrent.next;
            }
            resultCurrent = resultCurrent.next;
        }

        //走完遍历之后，将没有遍历完成的多项式中所有没有遍历到的元素最后一起放在链表最后
        while (thisCurrent != null) {
            resultCurrent.next = new Node<>(new Term(thisCurrent.value.coefficient, thisCurrent.value.exponent));
            thisCurrent = thisCurrent.next;
            resultCurrent = resultCurrent.next;
        }
        while (otherCurrent != null) {
            resultCurrent.next = new Node<>(new Term(otherCurrent.value.coefficient, otherCurrent.value.exponent));
            otherCurrent = otherCurrent.next;
            resultCurrent = resultCurrent.next;
        }

        return new OneLetterPolynomial(resultList);
    }

    @Override
    public String toString() {
        String temp = "";
        Node<Term> current = polynomial.head.next;
        while (current != null) {
            if (current.value.coefficient != 0) {
                if (!temp.equals("")) {
                    if (current.value.coefficient > 0) {
                        temp += " + " + current.value.coefficient;
                    } else {
                        temp += " - " + Math.abs(current.value.coefficient);
                    }
                } else {
                    if (current.value.coefficient < 0) {
                        temp += "-" + Math.abs(current.value.coefficient);
                    } else {
                        temp += current.value.coefficient;
                    }
                }
                if (current.value.exponent != 0) {
                    temp += "x";
                    if (current.value.exponent != 1) {
                        temp += "^" + current.value.exponent;
                    }
                }
            }
            current = current.next;
        }
        return temp.equals("") ? "0" : temp;
    }

    public static void main(String[] args) {
        SequenceList<double []> list_1 = new SequenceList<double []>(8);
        list_1.append(new double[]{10,0});
        list_1.append(new double[]{1,1});
        list_1.append(new double[]{3,2});
        list_1.append(new double[]{5,4});
        OneLetterPolynomial polynomial_1 = new OneLetterPolynomial(list_1);
        System.out.println(polynomial_1);

        SequenceList<double []> list_2 = new SequenceList<double []>(8);
        list_2.append(new double[]{-16,0});
        list_2.append(new double[]{1,1});
        list_2.append(new double[]{3,3});
        list_2.append(new double[]{5,5});
        OneLetterPolynomial polynomial_2 = new OneLetterPolynomial(list_2);
        System.out.println(polynomial_2);

        OneLetterPolynomial polynomial_3 = polynomial_1.addPolynomial(polynomial_2);
        System.out.println(polynomial_3);
    }

    //保存系数和指数的项类
    class Term {
        double coefficient;
        double exponent;

        public Term(double coefficient, double exponent) {
            this.coefficient = coefficient;
            this.exponent = exponent;
        }
    }
}
