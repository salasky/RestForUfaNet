package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.userValidation.UserValidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserValidate userValidate;
	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserValidate userValidate, UserRepository userRepository) {
		this.userValidate = userValidate;
		this.userRepository = userRepository;
	}

	@Override
	public ResponseEntity<String> createUser(User user) {

		if(!userValidate.isValidName(user.getName())){
			logger.error("Неправильный формат имени");
			return ResponseEntity.status((HttpStatus.FORBIDDEN)).body("Неправильный формат имени");
		}
		if(!userValidate.isValidPhoneNumber(user.getPhone())){
			logger.error("Неправильный формат номера");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Неправильный формат номера");
		}

		if (!userValidate.isValidEmail(user.getEmail())){
			logger.error("Неправильный формат email");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Неправильный формат email");
		}

		 userRepository.save(user);
		 return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь "+user.getName()+" создан\n");
	}


	@Override
	public List<UserDTO> getAllUser() {
		List<User> list = userRepository.findAll();
		List<UserDTO> userDTOList=new ArrayList<>();
		if(list!=null){
			userDTOList=list.stream().map(l->new UserDTO(l.getId(),l.getName())).collect(Collectors.toList());
		}
		return userDTOList;
	}

	@Override
	public Optional<User> getUserById(long id) {
		Optional<User> user=userRepository.findById(id);
		if(user.isEmpty()) {
			logger.error("Пользователь с id " + id + " не найден");
		}
		return user;
	}

	@Override
	public void deleteUser(long id) {
		userRepository.deleteById(id);
		
	}

}
