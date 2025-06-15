package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lv.venta.model.Review;
import lv.venta.service.IReviewService;
import lv.venta.service.IRestaurantService;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @Autowired
    private IRestaurantService restaurantService;

    @GetMapping("/restaurant/{restaurantId}")
    public String getRestaurantReviews(@PathVariable long restaurantId, Model model) {
        try {
            model.addAttribute("restaurant", restaurantService.getRestaurantById(restaurantId));
            model.addAttribute("reviews", reviewService.getReviewsByRestaurantId(restaurantId));
            model.addAttribute("averageRating", reviewService.getAverageRatingOfRestaurant(restaurantId));
            return "reviews";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/restaurants?error=" + e.getMessage();
        }
    }

    @GetMapping("/restaurant/{restaurantId}/add")
    public String showAddReviewForm(@PathVariable long restaurantId, Model model) {
        try {
            model.addAttribute("restaurant", restaurantService.getRestaurantById(restaurantId));
            model.addAttribute("review", new Review());
            return "review-form";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/restaurants?error=" + e.getMessage();
        }
    }

    @PostMapping("/restaurant/{restaurantId}")
    public String addReview(@PathVariable long restaurantId,
                          @Valid @ModelAttribute Review review,
                          BindingResult bindingResult,
                          Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            try {
                model.addAttribute("restaurant", restaurantService.getRestaurantById(restaurantId));
                return "review-form";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "redirect:/restaurants/" + restaurantId + "?error=" + e.getMessage();
            }
        }
        
        try {
            // TODO: Get current user ID from session
            long userId = 1L;
            reviewService.addReview(userId, restaurantId, review.getComment(), review.getRating());
            return "redirect:/reviews/restaurant/" + restaurantId + "?success=Review added successfully";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("restaurant", restaurantService.getRestaurantById(restaurantId));
            return "review-form";
        }
    }

    @GetMapping("/user")
    public String getUserReviews(Model model) {
        try {
            // TODO: Get current user ID from session
            long userId = 1L;
            List<Review> reviews = reviewService.getReviewsByRestaurantId(userId);
            model.addAttribute("reviews", reviews);
            return "user-reviews";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/users/profile?error=" + e.getMessage();
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteReview(@PathVariable long id, Model model) {
        try {
            reviewService.deleteReview(id);
            return "redirect:/reviews/user?success=Review deleted successfully";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/reviews/user?error=" + e.getMessage();
        }
    }
}