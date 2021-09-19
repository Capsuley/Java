package OODesign.Calculator.Operators.UnaryOperators;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

@Component
@Scope("singleton")
public class Sqrt extends UnaryOperator{

    public void operate(Stack<Stack<BigDecimal>> stack, int position) throws ArithmeticException {
        if(!stack.isEmpty() && !stack.peek().isEmpty()){
            Stack<BigDecimal> copy = (Stack<BigDecimal>)stack.peek().clone();
            BigDecimal top = copy.pop();
            try{
                BigDecimal sqrtedNumber =  top.sqrt(MathContext.UNLIMITED);
                sqrtedNumber.setScale(Math.min(sqrtedNumber.scale(), 16), RoundingMode.DOWN);
                copy.push(sqrtedNumber);
                stack.push(copy);
            } catch (Exception e){
                if(top.compareTo(new BigDecimal(0))>0){
                    BigDecimal bd = new BigDecimal(Math.sqrt(top.doubleValue()));
                    bd = bd.setScale(16, RoundingMode.DOWN);
                    copy.push(bd);
                    stack.push(copy);
                }else{
                    throw new IllegalArgumentException(String.format("operator sqrt (position: %s): support only real number", position));
                }
            }
        }else{
            throw new IllegalArgumentException(String.format("operator sqrt (position: %s): insufficient parameters", position));
        }
    }
}
