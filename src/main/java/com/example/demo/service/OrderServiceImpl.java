package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.dto.ClientIdDatetime;
import com.example.demo.dto.OrderIdDTO;
import com.example.demo.dto.TimeCountDTO;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.validator.DateValidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**

 *
 * @author salasky
 * https://github.com/salasky/
 */
@Service
public class OrderServiceImpl implements OrderService{
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
    public TimeCountDTO getBusyTime(String date) {
        return null;
        /////////////////////////////////
    }

    @Override
    public ResponseEntity recordingClient(ClientIdDatetime clientIdDatetime) {

        //Проверка существования клиента
        if(userRepository.findById(clientIdDatetime.getClientId()).isEmpty()){
            logger.error("Попытка записи не существующего клиента");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с id "+ clientIdDatetime.getClientId()+" не существует");
        }

        //Проверка формата даты и времени
        if(!dateValidate.isValidDateTime(clientIdDatetime.getDatetime())){
            logger.error("Запись клиента с неправильным форматом даты или времени");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Неправильный формат даты \n" +
                    "Формат записи даты YYYY-MM-DD HH:MM");
        }

        String [] datesplit=clientIdDatetime.getDatetime().split("-|\s|:");
        String hours=datesplit[3];
        String mm=datesplit[4];

        //Проверка записи в рабочее время
        if (Integer.parseInt(hours)<9 ||Integer.parseInt(hours)>20){
            logger.error("Попытка записи клиента в нерабочее время");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Нельзя записаться в нерабочее время \n" +
                    "График работы с 09:00 до 20:00");
        }

        //Проверка записи к началу заплыва
        if(Integer.parseInt(mm)!=0){
            logger.error("Попытка записи клиента в не начало заплыва");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Пожалуйста,запишитесь к началу заплыва\n" +
                    "График работы с 09:00 до 20:00\n" +
                    "Запись возможна на на 10:00, 11:00 итд");
        }

        //Проверка количества записей на 1 час
        if(orderRepository.countOrderByDate(clientIdDatetime.getDatetime())>10) {
            logger.error("Попытка записи клиента при полной записи");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Извините запись на данное время заполнена\n");
        }


        long countUserRecord=orderRepository.countOrderByDateAndAndUser(clientIdDatetime.getDatetime(),userRepository.getReferenceById(clientIdDatetime.getClientId()));

        //Проверка повторной записи
        if(countUserRecord>1){
            logger.error("Попытка повторной записи на одно и то же время");
            return ResponseEntity.status(HttpStatus.CREATED).body("Вы уже записаны на данное время");
        }





        orderRepository.save(new Order(clientIdDatetime.getDatetime(),userRepository.getReferenceById(clientIdDatetime.getClientId())));
        logger.info("Запись клиента произошло успешно");
        Order order=orderRepository.findByDateAndAndUser(clientIdDatetime.getDatetime(),userRepository.getReferenceById(clientIdDatetime.getClientId()));
        OrderIdDTO orderIdDTO=new OrderIdDTO(order.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderIdDTO);
    }
}
