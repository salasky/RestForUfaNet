package com.example.demo.service;



import com.example.demo.domain.User;
import com.example.demo.domain.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
	
	public ResponseEntity<String> createUser(User user);
	public List<UserDTO> getAllUser();
	public Optional<User> getUserById(long id);
	public void deleteUser(long id);


}
