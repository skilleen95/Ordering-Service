package com.skilleen.firstmicroservice;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstMicroserviceApplication extends RouteBuilder {

	public static void main(String[] args) {
		SpringApplication.run(FirstMicroserviceApplication.class, args);
	}

	@Override
	public void configure() throws Exception {
		from("timer://exercise?period=50s")
				.log(LoggingLevel.INFO, "HELLO!");
	}

}
