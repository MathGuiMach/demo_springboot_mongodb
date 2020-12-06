package com.mathguimach.demo_springboot_mongodb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathguimach.demo_springboot_mongodb.domain.User;
import com.mathguimach.demo_springboot_mongodb.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository uRep;
	
	public List<User> findAll(){
		return uRep.findAll();
	}
}
