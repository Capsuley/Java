package OODesign.Calculator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;


@Component
@Scope("singleton")
public class StackPrinter {

    public String printCurrentStack(Stack<Stack<BigDecimal>> multiVersionsStack){
        if(multiVersionsStack.isEmpty()){
            return "stack: ";
        }else{
            try{
                Stack<BigDecimal> currentStack = multiVersionsStack.peek();
                Stack<BigDecimal> tmp = new Stack<>();
                StringBuilder sb = new StringBuilder();
                while(!currentStack.isEmpty()){
                    tmp.add(currentStack.pop());
                }
                while(!tmp.isEmpty()){
                    BigDecimal popped = tmp.pop();
                    BigDecimal truncated = popped.setScale(Math.min(10, popped.scale()), RoundingMode.DOWN);
                    sb.append(truncated.toString());sb.append(" ");
                    currentStack.push(popped);
                }
                return "stack: " + sb.toString();
            } catch (NumberFormatException e){
                // not gonna happen
                return e.getMessage();
            }
        }
    }
}
