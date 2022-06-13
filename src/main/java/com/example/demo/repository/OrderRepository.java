package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    long countByDateAndTime(String date,String time);
    long countByDateAndUser(String date,User user);
    Order findByDateAndUser(String date, User user);
    Order findByDateAndTimeAndUser(String date,String time,User user);

    long countByDate(String date);
    long countByDateAndTimeAndUser(String date,String time,User user);


}
