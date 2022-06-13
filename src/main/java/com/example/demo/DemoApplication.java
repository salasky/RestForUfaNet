package com.example.demo;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**

 *
 * @author salasky
 * https://github.com/salasky/
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        var context=SpringApplication.run(DemoApplication.class, args);
        System.out.println(context.getBean(OrderRepository.class).countByDate("2022-06-11"));


    }

}
