package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.validator.UserValidate;
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
/**

 *
 * @author salasky
 * https://github.com/salasky/
 */
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

		User existingUser1 = getUserByEmail(user.getEmail());
		if (null != existingUser1) {
			logger.error("Пользователь с email "+existingUser1.getEmail()+" уже существует");
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь с email "+existingUser1.getEmail()+" уже существует");
		}

		User existingUser2 = getUserByPhone(user.getPhone());
		if (null != existingUser2) {
			logger.error("Пользователь с номером "+existingUser2.getPhone()+" уже существует");
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь с номером "+existingUser2.getPhone()+" уже существует");
		}

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
		 logger.info("Пользователь "+user.getName()+" создан");
		 return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь "+user.getName()+" создан\n");
	}


	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	public User getUserByPhone(String phone) {
		return userRepository.findByPhone(phone);
	}


	@Override
	public List<UserDTO> getAllUser() {
		List<User> list = userRepository.findAll();
		List<UserDTO> userDTOList=new ArrayList<>();
		if(list!=null){
			userDTOList=list.stream().map(l->new UserDTO(l.getId(),l.getName())).collect(Collectors.toList());
		}
		logger.info("Выдан список клиентов");
		return userDTOList;
	}

	@Override
	public Optional<User> getUserById(long id) {
		Optional<User> user=userRepository.findById(id);
		if(user.isEmpty()) {
			logger.error("Пользователь с id " + id + " не найден");
		}
		if(!user.isEmpty()) {
			logger.info("Выдана информация о пользователе c id "+id);
		}

		return user;
	}

	@Override
	public ResponseEntity<String> updateUser(User user) {

		if(!userRepository.findById(user.getId()).isEmpty()) {
			User existingUser4 = getUserByEmail(user.getEmail());
			if (null != existingUser4 && existingUser4.getId() != user.getId()) {
				logger.error("Пользователь с email " + existingUser4.getEmail() + " уже существует");
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь с email " + existingUser4.getEmail() + " уже существует");
			}

			User existingUser3 = getUserByPhone(user.getPhone());
			if (null != existingUser3 && existingUser3.getId() != user.getId()) {
				logger.error("Пользователь с номером " + existingUser3.getPhone() + " уже существует");
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь с номером " + existingUser3.getPhone() + " уже существует");
			}

			if (!userValidate.isValidName(user.getName())) {
				logger.error("Неправильный формат имени");
				return ResponseEntity.status((HttpStatus.FORBIDDEN)).body("Неправильный формат имени");
			}
			if (!userValidate.isValidPhoneNumber(user.getPhone())) {
				logger.error("Неправильный формат номера");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Неправильный формат номера");
			}

			if (!userValidate.isValidEmail(user.getEmail())) {
				logger.error("Неправильный формат email");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Неправильный формат email");
			}

			userRepository.save(user);
			logger.info("Данные пользователя " + user.getName() + " обновлены");
			return ResponseEntity.status(HttpStatus.CREATED).body("Данные пользователя обновлены на \n" +
					"id: " + user.getId() + "\n" +
					"name: " + user.getName() + "\n" +
					"email: " + user.getEmail() + "\n" +
					"phone: " + user.getPhone() + "\n"
			);
		}
		else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Пользователь с id "+user.getId()+ " не существует");
	}


	@Override
	public ResponseEntity deleteUser(User user) {
		Optional<User> user1=userRepository.findById(user.getId());
		if(!user1.isEmpty()){
			userRepository.deleteById(user.getId());
			logger.info("Пользователь с id "+user.getId()+ " удален");
			return ResponseEntity.status(HttpStatus.OK).body("Пользователь с id "+user.getId()+ " удален");
		}
		else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет пользователя с id "+user.getId());

	}

}
