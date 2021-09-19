package OODesign.Calculator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@Slf4j
@SpringBootApplication
public class CalculatorApplication implements CommandLineRunner {

	@Autowired
	Calculator calculator;

	public static void main(String[] args) {
		log.info("STARTING THE APP");
		SpringApplication.run(CalculatorApplication.class, args);
		log.info("APP FINISHED");
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		while(true){
			log.info("Input your command");
			String command = scanner.nextLine();
			if(command.equals("exit"))
				break;
			calculator.takeCommandsAndExecute(command);
		}
	}
}
