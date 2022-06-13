package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderIdDTO {
    private long orderId;

    public OrderIdDTO(long orderId) {
        this.orderId = orderId;
    }
}
