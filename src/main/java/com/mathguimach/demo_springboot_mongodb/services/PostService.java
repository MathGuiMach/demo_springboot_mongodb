package com.mathguimach.demo_springboot_mongodb.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathguimach.demo_springboot_mongodb.domain.Post;
import com.mathguimach.demo_springboot_mongodb.repositories.PostRepository;
import com.mathguimach.demo_springboot_mongodb.services.exceptions.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository pRep;
	
	public Post findById(String id) {
		Optional<Post> obj = pRep.findById(id);
		if(obj.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado!");
		}
		return obj.get();
	}
	
	public List<Post> findByTitle(String text){
		//return pRep.findByTitleContainingIgnoreCase(text);
		return pRep.searchTitle(text);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate){
		maxDate = new Date(maxDate.getTime() + 24*60*60*1000);
		return pRep.fullSearch(text, minDate, maxDate);
	}
}
