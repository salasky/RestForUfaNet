package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    long countOrderByDate(String date);
    Order findByDateAndAndUser(String date, User user);
    long countOrderByDateAndAndUser(String date,User user);
    List<Order> findAllByUserId(long id);
    List<Long> getAllByUserId(long id);
}
