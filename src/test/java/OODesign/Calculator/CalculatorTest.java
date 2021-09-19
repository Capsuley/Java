package OODesign.Calculator;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Logger;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CalculatorApplication.class, initializers = ConfigDataApplicationContextInitializer.class)
public class CalculatorTest {

    @Autowired
    Calculator calculator;

    private ListAppender<ILoggingEvent> appender;
    private Logger calcLogger = (Logger) LoggerFactory.getLogger(Calculator.class);

    @BeforeEach
    public void setUp() {
        appender = new ListAppender<>();
        appender.start();
        calcLogger.addAppender(appender);
    }

    @AfterEach
    public void tearDown() {
        calcLogger.detachAppender(appender);
    }

    @Test
    void validCommands_verified(){
        // pure number, clear
        String commands = "5 2";
        calculator.takeCommandsAndExecute(commands);
        String msg = calculator.printCurrentStack();
        assertTrue(msg.equals("stack: 5 2 "));
        calculator.takeCommandsAndExecute("clear");
        msg = calculator.printCurrentStack();
        assertTrue(msg.equals("stack: "));

        // +,-,*,/,undo
        commands = "1 2 3 4 5 + -";
        calculator.takeCommandsAndExecute(commands);
        msg = calculator.printCurrentStack();
        assertTrue(msg.equals("stack: 1 2 -6 "));

        commands = "* /";
        calculator.takeCommandsAndExecute(commands);
        msg = calculator.printCurrentStack();
        assertTrue(msg.equals("stack: -0.0833333333 "));

        commands = "undo";
        calculator.takeCommandsAndExecute(commands);
        msg = calculator.printCurrentStack();
        assertTrue(msg.equals("stack: 1 -12 "));

        commands = "2 sqrt";
        calculator.takeCommandsAndExecute(commands);
        msg = calculator.printCurrentStack();
        assertTrue(msg.equals("stack: 1 -12 1.4142135623 "));
    }

    @Test
    void unsupportedCommands_verified(){
        String commands = "?";
        calculator.takeCommandsAndExecute(commands);

        String errorMsg = "Unsupported command ? (position: 1)";
        String stackMsg = "stack: ";
        assertThat(appender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .containsExactly(errorMsg, stackMsg);
    }

    @Test
    void insufficientCommandsForSqrt_verified(){
        String commands = "sqrt";
        calculator.takeCommandsAndExecute(commands);

        String errorMsg = "operator sqrt (position: 1): insufficient parameters";
        String stackMsg = "stack: ";
        assertThat(appender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .containsExactly(errorMsg, stackMsg);
    }

    @Test
    void invalidStackForSqrt_verified(){
        String commands = "-1 sqrt";
        calculator.takeCommandsAndExecute(commands);

        String errorMsg = "operator sqrt (position: 4): support only real number";
        String stackMsg = "stack: -1 ";
        assertThat(appender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .containsExactly(errorMsg, stackMsg);
    }

    @Test
    void insufficientCommandsForBinaryOperators_verified(){
        String commands = "5 /";
        calculator.takeCommandsAndExecute(commands);

        String errorMsg = "operator / (position: 3): insufficient parameters";
        String stackMsg = "stack: 5 ";
        assertThat(appender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .containsExactly(errorMsg, stackMsg);
    }

    @Test
    void invalidDivision_verified(){
        String commands = "1 0 /";
        calculator.takeCommandsAndExecute(commands);

        String errorMsg = "Divisor cannot be 0";
        String stackMsg = "stack: 1 0 ";
        assertThat(appender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .containsExactly(errorMsg, stackMsg);
    }

}
