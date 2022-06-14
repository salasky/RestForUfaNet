package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.validator.UserValidate;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Сервис для работы с клиентами")
@SpringBootTest(classes = {UserServiceImpl.class})

class UserServiceImplTest {

    private static final long ID = 3;
    private static final String NAME = "Kate";
    private static final String EMAIL = "kate@gmail.com";
    private static final String PHONE = "89098736543";

    private static final User TEST_USER=new User(ID,NAME,EMAIL,PHONE);



    @Autowired
    private UserService userService;


    @MockBean
    private UserValidate userValidate;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {

        Mockito.when(userRepository.findById(TEST_USER.getId()))
                .thenReturn(Optional.of(TEST_USER));
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(TEST_USER);

    }


    @DisplayName("Поиск пользователя по ID (успех)")
    @Test
    public void findById_Success() {
        User generUser=userRepository.save(TEST_USER);
        Optional<User> foundUser=userRepository.findById(generUser.getId());
        Assertions.assertNotNull(foundUser.get());
        Assertions.assertEquals(generUser,foundUser.get());
    }
}
