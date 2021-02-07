package com.mathguimach.demo_springboot_mongodb.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.mathguimach.demo_springboot_mongodb.domain.Post;
import com.mathguimach.demo_springboot_mongodb.domain.User;
import com.mathguimach.demo_springboot_mongodb.dto.CommentDTO;
import com.mathguimach.demo_springboot_mongodb.dto.AuthorDTO;
import com.mathguimach.demo_springboot_mongodb.repositories.PostRepository;
import com.mathguimach.demo_springboot_mongodb.repositories.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private PostRepository pRep;
	
	@Autowired
	private UserRepository uRep;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		uRep.deleteAll();
		pRep.deleteAll();
		
		User m = new User(null,"Matheus","m@gmail.com");
		User p = new User(null,"Pedro","p@hotmail.com");
		User s = new User(null,"Sérgio","s@outlook.com");
		
		uRep.saveAll(Arrays.asList(m,p,s));
		
		Post p1 = new Post(null,sdf.parse("21/03/2018"),"Partiu Viagem", "Vou viajar para são paulo, abraços!", new AuthorDTO(m));
		Post p2 = new Post(null,sdf.parse("23/03/2018"),"Bom dia", "Acordei feliz hoje!",new AuthorDTO(m));
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!",sdf.parse("21/03/2018"), new AuthorDTO(p));
		CommentDTO c2 = new CommentDTO("Aproveite.",sdf.parse("22/03/2018"), new AuthorDTO(p));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!",sdf.parse("23/03/2018"), new AuthorDTO(s));
		
		p1.getComments().addAll(Arrays.asList(c1,c2));
		p2.getComments().addAll(Arrays.asList(c3));
		
		pRep.saveAll(Arrays.asList(p1,p2));
		
		m.getPosts().addAll(Arrays.asList(p1,p2));
		
		uRep.save(m);
	
	}

}
