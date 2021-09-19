package OODesign.Calculator;

import OODesign.Calculator.Operators.UnaryOperators.Clear;
import OODesign.Calculator.Operators.UnaryOperators.Sqrt;
import OODesign.Calculator.Operators.UnaryOperators.Undo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CalculatorApplication.class, initializers = ConfigDataApplicationContextInitializer.class)
public class UnaryOperatorsTest {

    @Autowired
    private Clear clear;
    @Autowired
    private Sqrt sqrt;
    @Autowired
    private Undo undo;

    @Test
    void clear_verified(){
        Stack<Stack<BigDecimal>> stack = new Stack<>();
        Stack<BigDecimal> subStack = new Stack<>();
        stack.push(subStack);
        clear.operate(stack, 0);
        assertTrue(stack.isEmpty());
        clear.operate(stack,0);
        assertTrue(stack.isEmpty());
    }

    @Test
    void sqrt_verified(){
        Stack<Stack<BigDecimal>> stack = new Stack<>();
        Stack<BigDecimal> subStack = new Stack<>();
        subStack.push(new BigDecimal("9"));
        stack.push(subStack);
        sqrt.operate(stack, 0);
        BigDecimal top = stack.peek().peek();
        assertTrue(Double.compare(top.doubleValue(), 3)==0);

        sqrt.operate(stack, 0);
        BigDecimal bd1 = stack.peek().peek();
        BigDecimal bd2 = new BigDecimal(Math.sqrt(3)).setScale(16, RoundingMode.DOWN);
        assertTrue(bd1.equals(bd2));

    }

    @Test
    void undo_verified(){
        Stack<Stack<BigDecimal>> stack = new Stack<>();
        Stack<BigDecimal> subStack1 = new Stack<>();
        subStack1.push(new BigDecimal("5"));
        stack.push(subStack1);

        Stack<BigDecimal> subStack2 = new Stack<>();
        subStack2.push(new BigDecimal("5"));subStack2.push(new BigDecimal("4"));
        stack.push(subStack2);
        sqrt.operate(stack, 0);

        assertTrue(stack.peek().peek().equals(new BigDecimal("2")));
        undo.operate(stack, 0);
        assertTrue(stack.peek().peek().equals(new BigDecimal("4")));
        undo.operate(stack, 0);
        assertTrue(stack.peek().peek().equals(new BigDecimal("5")));
        undo.operate(stack, 0);
        assertTrue(stack.isEmpty());
    }
}
