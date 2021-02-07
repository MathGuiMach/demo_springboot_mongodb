package com.mathguimach.demo_springboot_mongodb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathguimach.demo_springboot_mongodb.domain.User;
import com.mathguimach.demo_springboot_mongodb.dto.UserDTO;
import com.mathguimach.demo_springboot_mongodb.repositories.UserRepository;
import com.mathguimach.demo_springboot_mongodb.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository uRep;
	
	public List<User> findAll(){
		return uRep.findAll();
	}
	
	public User findById(String id) {
		
		Optional<User> user = uRep.findById(id);
		if(user.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado!");
		}
		return user.get();
		
	}
	
	public User insert(User obj) {
		return uRep.insert(obj);
	}
	
	public void deleteById(String id) {
		findById(id);//Se não encontrar o id, já joga uma excessão
		uRep.deleteById(id);
	}
	
	public User update(User obj) {
		User newObj = findById(obj.getId());
		updateData(newObj,obj);
		return uRep.save(newObj);
	}
	
	
	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(),objDto.getName(),objDto.getEmail());
	}
	
	
}
