package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientIdOrderIdDTO {
    private long clientId;
    private String orderId;
}
