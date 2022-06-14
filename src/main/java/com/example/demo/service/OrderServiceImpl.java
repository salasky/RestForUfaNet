package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.dto.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.validator.DateValidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author salasky
 * https://github.com/salasky/
 */
@Service
public class OrderServiceImpl implements OrderService {
    Logger logger = LoggerFactory.getLogger(OrderService.class);

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private DateValidate dateValidate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, DateValidate dateValidate) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.dateValidate = dateValidate;
    }


    @Override
    public ResponseEntity getBusyTime(String date) {
        //Проверка формата даты
        if (!dateValidate.isValidDate(date)) {
            logger.error("Попытка получения занятых записей с неправильным форматом даты");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Неправильный формат даты \n" +
                    "Формат записи даты YYYY-MM-DD");
        }
        logger.info("Выдан список занятых записей к " + date);
        var timeCountDTO = orderRepository.countBusyTime(date);
        return ResponseEntity.status(HttpStatus.OK).body(timeCountDTO);
    }


    @Override
    public ResponseEntity getAvailableTime(String date) {
        //Проверка формата даты
        if (!dateValidate.isValidDate(date)) {
            logger.error("Попытка получения свободных записей с неправильным форматом даты");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Неправильный формат даты \n" +
                    "Формат записи даты YYYY-MM-DD");
        }


        logger.info("Выдан список свободных записей к " + date);
        var timeCountDTO = orderRepository.countAvailableTime(date);
        return ResponseEntity.status(HttpStatus.OK).body(timeCountDTO);


    }

    @Override
    public ResponseEntity recordingClient(ClientIdDatetimeDTO clientIdDatetime) {

        //Проверка существования клиента
        if (userRepository.findById(clientIdDatetime.getClientId()).isEmpty()) {
            logger.error("Попытка записи не существующего клиента");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с id " + clientIdDatetime.getClientId() + " не существует");
        }

        //Проверка формата даты и времени
        if (!dateValidate.isValidDateTime(clientIdDatetime.getDatetime())) {
            logger.error("Запись клиента с неправильным форматом даты или времени");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Неправильный формат даты \n" +
                    "Формат записи даты YYYY-MM-DD HH:MM");
        }

        String[] datesplit = clientIdDatetime.getDatetime().split("-|\s|:");
        String hours = datesplit[3];
        String mm = datesplit[4];

        //Проверка записи в рабочее время
        if (Integer.parseInt(hours) < 9 || Integer.parseInt(hours) > 20) {
            logger.error("Попытка записи клиента в нерабочее время");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Пожалуйста,запишитесь в рабочее время \n" +
                    "График работы с 09:00 до 20:00");
        }

        //Проверка записи к началу заплыва
        if (Integer.parseInt(mm) != 0) {
            logger.error("Попытка записи клиента в не начало заплыва");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Пожалуйста,запишитесь к началу заплыва\n" +
                    "График работы с 09:00 до 20:00\n" +
                    "Запись возможна на на 10:00, 11:00 итд");
        }


        String[] dataAndTime = clientIdDatetime.getDatetime().split("\s");
        String date = dataAndTime[0];
        String time = dataAndTime[1];

        //Проверка количества записей на 1 час
        if (orderRepository.countOrderByDateAndTime(date,time) > 10) {
            logger.error("Попытка записи клиента при полной записи");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Извините запись на "+time+" заполнена\n");
        }


        Order existOrder = orderRepository.findByDateAndTimeAndUser(date, time, userRepository.getReferenceById(clientIdDatetime.getClientId()));
        //Проверка повторной записи
        if (existOrder != null) {
            logger.error("Попытка повторной записи на одно и то же время");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Вы уже записаны на данное время");
        }


        //Проверка записи клиента больше 2 раза за день
        if (orderRepository.countByDateAndUser(date, userRepository.getReferenceById(clientIdDatetime.getClientId())) >= 2) {
            logger.error("Попытка записи более 2 раз в один день");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Извините, в день возможна запись только 1 раз длительностью до 2-х часов");
        }


        //Проверка записи клиента больше 1 раза за день c разницей более 1 часа
        if (orderRepository.countByDateAndUser(date, userRepository.getReferenceById(clientIdDatetime.getClientId())) >= 1) {
            if (Math.abs(Integer.parseInt(time.split(":")[0]) -
                    Integer.parseInt(orderRepository.findByDateAndUser(date, userRepository.getReferenceById(clientIdDatetime.getClientId())).getTime()
                            .split(":")[0])) > 1) {

                logger.error("Попытка записи более 2 раз в один день7");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Извините, в день возможна запись только 1 раз длительностью до 2-х часов");
            }
        }

        //запись в бд
        long id = orderRepository.save(new Order(date, time, userRepository.getReferenceById(clientIdDatetime.getClientId()))).getId();
        logger.info("Запись клиента произошла успешно");
        OrderIdDTO orderIdDTO = new OrderIdDTO(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderIdDTO);
    }

    @Override
    public ResponseEntity cancelingRecord(ClientIdOrderIdDTO clientIdOrderIdDTO) {
        if (orderRepository.findByIdAndUser(Long.parseLong(clientIdOrderIdDTO.getOrderId()), userRepository.getReferenceById(clientIdOrderIdDTO.getClientId())) == null) {
            logger.error("Запись или клиент с данными id не найдена");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Запись или клиент с данными id не найдена");

        }
        logger.info("Запись с ClientId " + clientIdOrderIdDTO.getClientId() + " OrderId " + clientIdOrderIdDTO.getOrderId() + " удалена");
        orderRepository.deleteOrderByIdAndUser(Long.parseLong(clientIdOrderIdDTO.getOrderId()), userRepository.getReferenceById(clientIdOrderIdDTO.getClientId()));
        return ResponseEntity.status(HttpStatus.OK).body("Запись с ClientId " + clientIdOrderIdDTO.getClientId() + "\n" +
                "OrderId " + clientIdOrderIdDTO.getOrderId() + " удалена");
    }

    @Override
    public ResponseEntity findByNameAndDate() {
        return null;
    }
}
