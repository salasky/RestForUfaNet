package com.example.demo.service;

import com.example.demo.dto.ClientIdDatetimeDTO;
import com.example.demo.dto.ClientIdOrderIdDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    public ResponseEntity getBusyTime(String date);

    public ResponseEntity getAvailableTime(String date);

    public ResponseEntity recordingClient(ClientIdDatetimeDTO clientIdDatetime);

    public ResponseEntity cancelingRecord(ClientIdOrderIdDTO clientIdOrderIdDTO);

    public  ResponseEntity findByNameAndDate();
}
