package com.codifi.cp2;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@WebServlet("/hello")
@SpringBootApplication
@EnableWebMvc
public class Cp2Application extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        SpringApplication.run(Cp2Application.class, args);
    }

}
