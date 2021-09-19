package OODesign.Calculator;

import OODesign.Calculator.Operators.UnaryOperators.Sqrt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CalculatorApplication.class, initializers = ConfigDataApplicationContextInitializer.class)
public class StackPrinterTest {
    @Autowired
    private StackPrinter printer;
    @Autowired
    private Sqrt sqrt;

    @Test
    void logCurrentStack_verified(){
        Stack<Stack<BigDecimal>> stack = new Stack<>();
        var msg = printer.printCurrentStack(stack);
        assertTrue(msg.equals("stack: "));

        Stack<BigDecimal> subStack = new Stack<>();
        subStack.push(new BigDecimal("5"));subStack.push(new BigDecimal("2"));
        stack.push(subStack);
        msg = printer.printCurrentStack(stack);
        assertTrue(msg.equals("stack: 5 2 "));

        sqrt.operate(stack, 0);
        msg = printer.printCurrentStack(stack);
        BigDecimal bd = new BigDecimal(Math.sqrt(2));
        bd = bd.setScale(10, RoundingMode.DOWN);
        String toCompare = String.format("stack: 5 %s ", bd);
        assertTrue(msg.equals(toCompare));
    }
}
