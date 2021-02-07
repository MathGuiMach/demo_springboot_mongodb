package com.mathguimach.demo_springboot_mongodb.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mathguimach.demo_springboot_mongodb.domain.Post;
import com.mathguimach.demo_springboot_mongodb.domain.User;
import com.mathguimach.demo_springboot_mongodb.dto.UserDTO;
import com.mathguimach.demo_springboot_mongodb.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResource {

	@Autowired
	private UserService uServ;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll(){
		List<User> list = uServ.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<UserDTO> findById(@PathVariable String id){
		User u = uServ.findById(id);
		return ResponseEntity.ok().body(new UserDTO(u));
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDto){
		User u = uServ.fromDTO(objDto);
		u = uServ.insert(u);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(u.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable String id){
		uServ.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody UserDTO objDto,@PathVariable String id){
		User u = uServ.fromDTO(objDto);
		u.setId(id);
		u = uServ.update(u);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/posts",method=RequestMethod.GET)
	public ResponseEntity<List<Post>> findPostsByUserId(@PathVariable String id){
		User u = uServ.findById(id);
		return ResponseEntity.ok().body(u.getPosts());
	}
}
