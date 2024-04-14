package com.in.user.external;

import com.in.user.entities.Hotel;
import com.in.user.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="RATING-SERVICE")
@Service
public interface RatingService {

    @PostMapping("/ratings")
    Rating createRating(@RequestBody Rating values);

    @PutMapping("/ratings/{ratingId}")
    Rating updateRating(@PathVariable("ratingId") String ratingId,Rating rating);

    @DeleteMapping("/ratings/{ratingId}")
    public void deleteRating(@PathVariable String ratingId);
}
