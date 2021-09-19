package OODesign.Calculator.Operators;

import OODesign.Calculator.Operators.BinaryOperators.*;
import OODesign.Calculator.Operators.UnaryOperators.Clear;
import OODesign.Calculator.Operators.UnaryOperators.Sqrt;
import OODesign.Calculator.Operators.UnaryOperators.UnaryOperator;
import OODesign.Calculator.Operators.UnaryOperators.Undo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("singleton")
public class OperatorsMap {
    @Autowired
    private Addition addition;
    @Autowired
    private Subtraction subtraction;
    @Autowired
    private Multiplication multiplication;
    @Autowired
    private Division division;
    @Autowired
    private Clear clear;
    @Autowired
    private Sqrt sqrt;
    @Autowired
    private Undo undo;

    public Map<String, BinaryOperator> binaryOps;
    public Map<String, UnaryOperator> unaryOps;

    @PostConstruct
    public void initializeOperatorsMap(){
        binaryOps = new HashMap<>();
        binaryOps.put("+", addition);binaryOps.put("-", subtraction);binaryOps.put("*",multiplication);binaryOps.put("/", division);
        unaryOps = new HashMap<>();
        unaryOps.put("clear", clear);unaryOps.put("sqrt", sqrt);unaryOps.put("undo", undo);
    }
}
