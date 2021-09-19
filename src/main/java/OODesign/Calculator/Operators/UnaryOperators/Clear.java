package OODesign.Calculator.Operators.UnaryOperators;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Stack;

@Component
@Scope("singleton")
public class Clear extends UnaryOperator{

    public void operate(Stack<Stack<BigDecimal>> stack, int position) {
        stack.clear();
    }
}
