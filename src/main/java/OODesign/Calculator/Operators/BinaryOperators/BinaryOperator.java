package OODesign.Calculator.Operators.BinaryOperators;

import java.math.BigDecimal;

public abstract class BinaryOperator {
    public abstract BigDecimal compute(BigDecimal a, BigDecimal b);
}
