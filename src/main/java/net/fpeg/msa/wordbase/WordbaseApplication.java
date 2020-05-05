package net.fpeg.msa.wordbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"net.fpeg.msa"})
public class WordbaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordbaseApplication.class, args);
    }

}
