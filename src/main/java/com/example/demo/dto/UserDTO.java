package com.example.demo.dto;

import lombok.*;

/**
 * @author salasky
 * https://github.com/salasky/
 */
@Getter
@Setter
@ToString
public class UserDTO {
    private long id;
    private String name;

    public UserDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

}
