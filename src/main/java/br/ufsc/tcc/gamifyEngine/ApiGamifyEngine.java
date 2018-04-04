package br.ufsc.tcc.gamifyEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan("br.ufsc.tcc.gamifyEngine")
@SpringBootApplication(scanBasePackages={"br.ufsc.tcc.gamifyEngine"})
public class ApiGamifyEngine 
{
	public static void main(String[] args) {
        SpringApplication.run(ApiGamifyEngine.class, args);
    }
}
