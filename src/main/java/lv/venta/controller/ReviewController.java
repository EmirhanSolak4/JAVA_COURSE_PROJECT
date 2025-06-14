package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lv.venta.model.Review;
import lv.venta.service.IReviewService;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Review>> getReviewsByRestaurant(@PathVariable long restaurantId) {
        try {
            return ResponseEntity.ok(reviewService.getReviewsByRestaurantId(restaurantId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> addReview(
            @RequestParam long userId,
            @RequestParam long restaurantId,
            @RequestParam String comment,
            @RequestParam int rating) {
        try {
            reviewService.addReview(userId, restaurantId, comment, rating);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/restaurant/{restaurantId}/rating")
    public ResponseEntity<Double> getRestaurantRating(@PathVariable long restaurantId) {
        try {
            return ResponseEntity.ok(reviewService.getAverageRatingOfRestaurant(restaurantId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 