package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
/**

 *
 * @author salasky
 * https://github.com/salasky/
 */

@Entity
@Table(name = "Clients")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(unique = true,name = "email")
    private String email;

    @Column(unique = true,name = "phone")
    private String phone;






}
