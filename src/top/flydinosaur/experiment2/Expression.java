package top.flydinosaur.experiment2;

public class Expression {

    private SequenceStack<Character> expression;

    public Expression(String expressionStr) {
        this.expression = new SequenceStack<Character>(expressionStr.length());
        for (int i = 0; i < expressionStr.length(); i++) {
            if (expressionStr.charAt(i) != ' ') {
                this.expression.push(expressionStr.charAt(i));
            }
        }
    }

    public Expression() {
        this.expression = new SequenceStack<Character>(10);
    }

    public SequenceStack<Character> getExpression() {
        return expression;
    }

    public void setExpression(SequenceStack<Character> expression) {
        this.expression = expression;
    }

    // 判断是否是数字
    private boolean isNumber(Character ch) {
        return ch >= '0' && ch <= '9';
    }

    // 获取优先级
    private int precedence(Character operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        }
        if (operator == '*' || operator == '/') {
            return 2;
        }
        return 0;
    }

    public void convertToPostfixExpression() {
        // 后缀表达式
        Expression postfixExpression = new Expression();
        // 运算符栈
        SequenceStack<Character> operatorStack = new SequenceStack<>(10);

        // 从栈底到栈顶遍历
        for (int i = expression.size() - 1; i >= 0; i--) {
            Character ch = expression.get(i);
            // 数字直接加入到后缀表达式中
            if (isNumber(ch)) {
                postfixExpression.getExpression().push(ch);
            }
            // 左括号压入运算符栈
            else if (ch == '(') {
                operatorStack.push(ch);
            }
            // 右括号，将栈顶元素弹出加入后缀表达式，直到遇到左括号
            else if (ch == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfixExpression.getExpression().push(operatorStack.pop());
                }
                operatorStack.pop(); // 弹出左括号
            }
            // 遇到运算符，比较栈顶运算符优先级，高优先级的弹出加入后缀表达式
            else {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(ch)) {
                    postfixExpression.getExpression().push(operatorStack.pop());
                }
                operatorStack.push(ch);
            }
        }

        // 最后弹出剩余的运算符
        while (!operatorStack.isEmpty()) {
            postfixExpression.getExpression().push(operatorStack.pop());
        }

        // 更新表达式为后缀表达式
        this.expression = postfixExpression.getExpression();
    }

    public double calculate() {
        SequenceStack<Double> valueStack = new SequenceStack<>(10);
        // 从栈底到栈顶遍历
        for (int i = expression.size() - 1; i >= 0; i--) {
            char ch = expression.get(i);
            // 如果是数字，将其转换为数值并入栈
            if (isNumber(ch)) {
                valueStack.push((double) Character.getNumericValue(ch));
            }
            // 如果是运算符，出栈两个值进行运算，结果再入栈
            else {
                double b = valueStack.pop();
                double a = valueStack.pop();
                double result = 0;

                if (ch == '+') {
                    result = a + b;
                } else if (ch == '-') {
                    result = a - b;
                } else if (ch == '*') {
                    result = a * b;
                } else if (ch == '/') {
                    if (b == 0) {
                        throw new ArithmeticException("除数不能为零。");
                    }
                    result = a / b;
                }
                valueStack.push(result);
            }
        }
        // 返回栈顶的最终结果
        return valueStack.pop();
    }

    @Override
    public String toString() {
        return expression.toString();
    }

    public static void main(String[] args) {
        Expression expression = new Expression("1*2+(3-4)*5");
        System.out.println("中缀表达式：" + expression);
        expression.convertToPostfixExpression();
        System.out.println("后缀表达式：" + expression);
        System.out.println("计算结果：" + expression.calculate());
    }
}
