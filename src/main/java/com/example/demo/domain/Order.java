package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    //Lazy т.к. не всегда нужна информация о клиентах
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Order(String date, User user) {
        this.date = date;
        this.user = user;
    }
}
