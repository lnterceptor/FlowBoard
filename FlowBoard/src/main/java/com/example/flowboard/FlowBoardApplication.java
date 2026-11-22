package com.example.flowboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlowBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowBoardApplication.class, args);
    }
/*
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("12345: " + encoder.encode("12345"));
        System.out.println("John123!: " + encoder.encode("John123!"));
        System.out.println("Emma2026!: " + encoder.encode("Emma2026!"));
        System.out.println("Alex1234!: " + encoder.encode("Alex1234!"));
        System.out.println("Sarah2026!: " + encoder.encode("Sarah2026!"));
        System.out.println("Mike123!: " + encoder.encode("Mike123!"));
    }*/
}
