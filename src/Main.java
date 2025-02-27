import top.flydinosaur.experiment1.OneLetterPolynomial;
import top.flydinosaur.experiment1.SequenceList;

public class Main {
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
}
