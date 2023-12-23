/**
 * This package contains the main application class for the Homeschool Interactive project.
 * 
 * This project is the original work of Jason Hatfield.
 * 
 */
package com.jasonhatfield.homeschoolinteractive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class of the application.
 * This class is responsible for starting the application.
 */
@SpringBootApplication
public class App {

    /**
     * The main method of the application.
     * It starts the Spring Boot application.
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
