package br.ufsc.tcc.gamifyEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@SpringBootApplication(scanBasePackages={"br.ufsc.tcc.gamifyEngine"})
public class ApiGamifyEngine 
{
	public static void main(String[] args) {
        SpringApplication.run(ApiGamifyEngine.class, args);
    }
}
