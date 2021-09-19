package OODesign.Calculator;

import OODesign.Calculator.Operators.OperatorsMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
@Scope("prototype")
public class Calculator {
    @Autowired
    private StackPrinter printer;
    @Autowired
    private OperatorsMap opsMap;
    private Stack<Stack<BigDecimal>> multiVersionsStack = new Stack<>();

    public void takeCommandsAndExecute(String str){
        if(str.trim().length()==0){
            log.info(printCurrentStack());
            return;
        }

        String[] commands = str.split(" ");
        int prePos = 1;
        try{
            for(String command:commands){
                try{
                    Integer.parseInt(command);
                    Stack<BigDecimal> newStack = multiVersionsStack.isEmpty()
                            ? new Stack<>()
                            : (Stack<BigDecimal>)multiVersionsStack.peek().clone();
                    newStack.push(new BigDecimal(command));
                    multiVersionsStack.push(newStack);
                }catch (NumberFormatException e){
                    executeCommand(command, prePos);
                }finally {
                    prePos += command.length();prePos++;
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            log.info(printCurrentStack());
        }
    }

    public String printCurrentStack(){
        return printer.printCurrentStack(multiVersionsStack);
    }

    private void executeCommand(String command, int position){
        if(opsMap.binaryOps.containsKey(command)){
            if(multiVersionsStack.isEmpty()){
                throw new IllegalArgumentException(String.format("operator %s (position: %s): insufficient parameters", command, position));
            }else{
                Stack<BigDecimal> newStack = (Stack<BigDecimal>)multiVersionsStack.peek().clone();
                if(newStack.size()<2){
                    throw new IllegalArgumentException(String.format("operator %s (position: %s): insufficient parameters", command, position));
                } else{
                    BigDecimal b = newStack.pop();
                    BigDecimal a = newStack.pop();
                    newStack.push(opsMap.binaryOps.get(command).compute(a, b));
                    multiVersionsStack.push(newStack);
                }
            }
        }else if(opsMap.unaryOps.containsKey(command)){
            opsMap.unaryOps.get(command).operate(multiVersionsStack, position);
        }else{
            throw new IllegalArgumentException(String.format("Unsupported command %s (position: %s)", command, position));
        }
    }

}
