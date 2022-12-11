package com.example.jpabeans.demojpabeans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class DemoJpaBeansApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJpaBeansApplication.class, args);

		// AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		// // Registering the beans(MyConfig.java class contains beans) with context
		// context.register(MyConfig.class);

		// // Now we need to refresh it, else you will get IllegialStateException
		// context.refresh();

		// // Call the getBean() method and specify the required bean which you are getting
		// HelloWorld obj1 = context.getBean(HelloWorld.class);
		// // HelloWorld obj2 = context.getBean(HelloWorld.class);

		// obj1.setMessage("This is an object");
		// // obj1.setData("ABC");

		// // Now you have got the bean simply print it
		// System.out.println(obj1);
		// // System.out.println(obj2);
	}

}
