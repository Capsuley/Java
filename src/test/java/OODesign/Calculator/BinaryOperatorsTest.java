package OODesign.Calculator;

import OODesign.Calculator.Operators.BinaryOperators.Addition;
import OODesign.Calculator.Operators.BinaryOperators.Division;
import OODesign.Calculator.Operators.BinaryOperators.Multiplication;
import OODesign.Calculator.Operators.BinaryOperators.Subtraction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CalculatorApplication.class, initializers = ConfigDataApplicationContextInitializer.class)
public class BinaryOperatorsTest {

    @Autowired
    private Addition addition;
    @Autowired
    private Subtraction subtraction;
    @Autowired
    private Multiplication multiplication;
    @Autowired
    private Division division;

    @Test
    void addition_verified(){
        BigDecimal a = new BigDecimal("5");
        BigDecimal b = new BigDecimal("3.1");
        BigDecimal res = addition.compute(a,b);
        assertTrue(res.compareTo(new BigDecimal("8.1"))==0);
    }

    @Test
    void subtraction_verified(){
        BigDecimal a = new BigDecimal("5");
        BigDecimal b = new BigDecimal("3.1");
        BigDecimal res = subtraction.compute(a,b);
        assertTrue(res.compareTo(new BigDecimal("1.9"))==0);
    }

    @Test
    void multiplication_verified(){
        BigDecimal a = new BigDecimal("5");
        BigDecimal b = new BigDecimal("3.1");
        BigDecimal res = multiplication.compute(a,b);
        assertTrue(res.compareTo(new BigDecimal("15.5"))==0);
    }

    @Test
    void division_verified(){
        BigDecimal a = new BigDecimal("5");
        BigDecimal b = new BigDecimal("2");
        BigDecimal res = division.compute(a,b);
        assertTrue(res.compareTo(new BigDecimal("2.5"))==0);

        BigDecimal c = new BigDecimal("3");
        res = division.compute(a, c);
        assertTrue(res.scale()==16);
    }

    @Test
    void division_throw_arithmeticException(){
        assertThrows(ArithmeticException.class, () -> {
            division.compute(new BigDecimal("5"), BigDecimal.ZERO);
        });
    }
}
