package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.persistence.*;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String date;

    private String time;

    //Lazy т.к. не всегда нужна информация о клиентах
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Order(String date, String time, User user) {
        this.date = date;
        this.time = time;
        this.user = user;
    }
}
