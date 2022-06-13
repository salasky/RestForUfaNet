package com.example.demo.service;

import com.example.demo.dto.ClientIdDatetime;
import com.example.demo.dto.OrderIdDTO;
import com.example.demo.dto.TimeCountDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    public TimeCountDTO getBusyTime(String date);
    public ResponseEntity recordingClient(ClientIdDatetime clientIdDatetime);
}
