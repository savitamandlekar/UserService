package com.in.user.Impl;

import com.in.user.Exception.ResourceNotFoundException;
import com.in.user.entities.Hotel;
import com.in.user.entities.Rating;
import com.in.user.entities.User;
import com.in.user.external.HotelService;
import com.in.user.repositoy.UserRepo;
import com.in.user.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    //private Logger log= (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomId=UUID.randomUUID().toString();

        user.setUserId(randomId);
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user= userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Id with given id not found"));

        //getRestTemplateCalling(user);
        getFeignClientCalling(user);

        return user ;
    }

    /** Feign Client Important*/
    private void getFeignClientCalling(User user) {
        Rating [] list=restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);

        List<Rating> ratings= Arrays.stream(list).toList();
        List<Rating> streamratingList=ratings.stream().map(rating->{
            //ResponseEntity<Hotel> hotelList= restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel= hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;

        }).collect(Collectors.toList());

        user.setRatings(streamratingList);
    }

    /** Rest Template */
    private void getRestTemplateCalling(User user) {
        Rating [] list=restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);

        List<Rating> ratings= Arrays.stream(list).toList();
        List<Rating> streamratingList=ratings.stream().map(rating->{
            ResponseEntity<Hotel> hotelList= restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel= hotelList.getBody();
            rating.setHotel(hotel);
            return rating;

        }).collect(Collectors.toList());

        user.setRatings(streamratingList);
    }
}