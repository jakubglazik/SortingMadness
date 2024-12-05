package pl.put.poznan.sortingMadness.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import pl.put.poznan.sortingMadness.rest.SortController;

@SpringBootApplication
@ComponentScan("pl.put.poznan.sortingMadness")
public class SortingMadness {
    public static void main(String[] args) {
        SpringApplication.run(SortingMadness.class, args);
    }
}

