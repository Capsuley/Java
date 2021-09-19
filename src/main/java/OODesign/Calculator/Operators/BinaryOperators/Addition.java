package OODesign.Calculator.Operators.BinaryOperators;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Scope("singleton")
public class Addition extends BinaryOperator{

    public BigDecimal compute(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }
}
