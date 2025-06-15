package lv.venta.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Restaurant;
import lv.venta.model.Review;
import lv.venta.model.User;
import lv.venta.repository.IRestaurantRepository;
import lv.venta.repository.IReviewRepository;
import lv.venta.repository.IUserRepository;
import lv.venta.service.IReviewService;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private IReviewRepository reviewRepo;

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private IRestaurantRepository restaurantRepo;

    @Override
    public List<Review> getAllReviews() {
        return (List<Review>) reviewRepo.findAll();
    }

    @Override
    public List<Review> getReviewsByRestaurantId(long restaurantId) throws Exception {
        Restaurant rest = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new Exception("Restaurant not found"));

        List<Review> results = new ArrayList<>();
        for (Review r : reviewRepo.findAll()) {
            if (r.getRestaurant().getId() == restaurantId) {
                results.add(r);
            }
        }

        return results;
    }

    @Override
    public List<Review> getReviewsByUserId(long userId) throws Exception {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        List<Review> results = new ArrayList<>();
        for (Review r : reviewRepo.findAll()) {
            if (r.getUser().getId() == userId) {
                results.add(r);
            }
        }

        return results;
    }

    @Override
    public void addReview(long userId, long restaurantId, String comment, int rating) throws Exception {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new Exception("Restaurant not found"));

        if (rating < 1 || rating > 5) {
            throw new Exception("Rating must be between 1 and 5");
        }

        Review review = new Review();
        review.setUser(user);
        review.setRestaurant(restaurant);
        review.setComment(comment);
        review.setRating(rating);

        reviewRepo.save(review);
    }

    @Override
    public double getAverageRatingOfRestaurant(long restaurantId) throws Exception {
        List<Review> reviews = getReviewsByRestaurantId(restaurantId);
        if (reviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }

        return sum / reviews.size();
    }

	@Override
	public List<Review> getUserReviews(long userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteReview(long id) {
		// TODO Auto-generated method stub
		
	}
}
