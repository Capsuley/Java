package OODesign.Calculator.Operators.BinaryOperators;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Scope("singleton")
public class Division extends BinaryOperator{

    public BigDecimal compute(BigDecimal a, BigDecimal b) {
        if(b.compareTo(BigDecimal.ZERO)==0){
            throw new ArithmeticException("Divisor cannot be 0");
        }
        BigDecimal res;
        try{
            res = a.divide(b);
        } catch (Exception e){
            res = a.divide(b, 16, RoundingMode.HALF_UP);
        }
        return res;
    }
}
