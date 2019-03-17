package com.springboothibernate.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboothibernate.model.User;
import com.springboothibernate.repository.UserRepository;

@Service
public class UserDAO {
	@Autowired
	UserRepository userRepository;
	
	/*to save an employee*/
	public User save(User std) {
		return userRepository.save(std);
	}
	/* search all employees*/
	public List<User> findAll(){
		return userRepository.findAll();
	}
	/*get an employee by id*/
	public User findOne(Integer id) {
		return userRepository.findOne(id);
	}
	/*delete an employee*/
	public void delete(User std) {
		userRepository.delete(std);
	}
	public User findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}
}