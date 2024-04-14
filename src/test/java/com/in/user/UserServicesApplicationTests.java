package com.in.user;

import com.in.user.entities.Rating;
import com.in.user.external.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServicesApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private RatingService service;

	@Test
	void createRating(){
		Rating rating=Rating.builder().rating(10).userId("").hotelId("").feedback("this is created using feign").build();

		Rating saveRating = service.createRating(rating);

		System.out.println("New Creating Rating:");

	}
}
