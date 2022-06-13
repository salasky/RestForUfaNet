package com.example.demo.service;

import com.example.demo.dto.ClientIdDatetimeDTO;
import com.example.demo.dto.ClientIdOrderIdDTO;
import com.example.demo.dto.TimeCountDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    public TimeCountDTO getBusyTime(String date);
    public ResponseEntity recordingClient(ClientIdDatetimeDTO clientIdDatetime);
    public ResponseEntity cancelingRecord(ClientIdOrderIdDTO clientIdOrderIdDTO);
}
