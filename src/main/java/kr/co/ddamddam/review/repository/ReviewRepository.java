package kr.co.ddamddam.review.repository;

import kr.co.ddamddam.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {


}
