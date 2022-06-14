package com.example.demo.rest;

import com.example.demo.dto.ClientIdDatetimeDTO;
import com.example.demo.dto.ClientIdOrderIdDTO;
import com.example.demo.dto.FindByNameDateDTO;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author salasky
 * https://github.com/salasky/
 */
@RestController
@RequestMapping("/api/v0/pool/timetable/")
@CrossOrigin("*")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/reserve")
    public ResponseEntity saveUser(@RequestBody ClientIdDatetimeDTO clientIdDatetime) {
        return orderService.recordingClient(clientIdDatetime);

    }

    @PostMapping("/cancel")
    public ResponseEntity cancelingRecord(@RequestBody ClientIdOrderIdDTO clientIdOrderIdDTO) {
        return orderService.cancelingRecord(clientIdOrderIdDTO);

    }

    @GetMapping("/all/{date}")
    @ResponseBody
    public ResponseEntity getBusyTime(@PathVariable String date) {
        return orderService.getBusyTime(date);
    }

    @GetMapping("/available/{date}")
    @ResponseBody
    public ResponseEntity getAvailableTime(@PathVariable String date) {
        return orderService.getAvailableTime(date);
    }

    @PostMapping("/find")
    public ResponseEntity findByNameAndDate(@RequestBody FindByNameDateDTO findByNameDateDTO) {
        return orderService.findByNameAndDate(findByNameDateDTO);

    }


}
