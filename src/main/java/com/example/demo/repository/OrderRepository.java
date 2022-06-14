package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.dto.TimeCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    long countByDateAndUser(String date, User user);

    List<Order> findByDateAndUser(String date, User user);



    Order findByDateAndTimeAndUser(String date, String time, User user);

    long countOrderByDateAndTime(String date,String time);

    Order findByIdAndUser(Long id, User user);

    Optional<Order> findOrderByUser(Optional<User> user);

    @Transactional
    void deleteOrderByUser(Optional<User> user);

    @Transactional
    void deleteOrderByIdAndUser(long id, User user);

    @Query("SELECT new com.example.demo.dto.TimeCountDTO(time, COUNT (time)) from Order where date=:datee group by date,time  order by time")
    List<TimeCountDTO> countBusyTime(String datee);

    @Query("SELECT new com.example.demo.dto.TimeCountDTO(time, 10-COUNT (time)) from Order where date=:datee group by date,time  order by time")
    List<TimeCountDTO> countAvailableTime(String datee);

}
