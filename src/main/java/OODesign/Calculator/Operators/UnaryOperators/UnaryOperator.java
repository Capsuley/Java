package OODesign.Calculator.Operators.UnaryOperators;

import java.math.BigDecimal;
import java.util.Stack;

public abstract class UnaryOperator {
    public abstract void operate(Stack<Stack<BigDecimal>> stack, int position);
}
