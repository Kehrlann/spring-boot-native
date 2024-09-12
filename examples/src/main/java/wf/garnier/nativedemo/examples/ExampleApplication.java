package wf.garnier.nativedemo.examples;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.annotation.ReflectiveScan;

@SpringBootApplication
@ImportRuntimeHints(ExamplesRuntimeHints.class)
@RegisterReflectionForBinding(TemplatingController.Book.class)
@ReflectiveScan
public class ExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}

}
