package com.example.demo.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeCountDTO {
    private String time;
    private long count;

    public TimeCountDTO(String time, long count) {
        this.time = time;
        this.count = count;
    }
}
