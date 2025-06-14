package lv.venta.service;

import java.util.List;

import lv.venta.model.*;


public interface IReviewService {

	List<Review> getAllReviews();

	List<Review> getReviewsByRestaurantId(long restaurantId) throws Exception;

	void addReview(long userId, long restaurantId, String comment, int rating) throws Exception;

	double getAverageRatingOfRestaurant(long restaurantId) throws Exception;

}
