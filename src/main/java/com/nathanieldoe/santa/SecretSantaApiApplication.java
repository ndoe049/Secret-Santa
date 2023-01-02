package com.nathanieldoe.santa;

import com.nathanieldoe.santa.db.PersonRepository;
import com.nathanieldoe.santa.model.Person;
import jakarta.annotation.PostConstruct;
import org.hibernate.cfg.CreateKeySecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@SpringBootApplication
public class SecretSantaApiApplication {

//	@Autowired
//	PersonRepository repo;
//
//	@PostConstruct
//	private void init() {
//		Person me = new Person("Nathan", "Doe", "nathandoe22@gmail.com");
//		Person spouse = new Person("Ali", "Doe", "alidoe0413@gmail.com");
//
//		Person savedMe = repo.save(me);
//		Person savedSpouse = repo.save(spouse);
//	}

	public static void main(String[] args) {
		SpringApplication.run(SecretSantaApiApplication.class, args);
	}

}
