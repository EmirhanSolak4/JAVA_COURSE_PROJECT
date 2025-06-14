package lv.venta.repository;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Review;

public interface IReviewRepository extends CrudRepository<Review, Long> {

}
