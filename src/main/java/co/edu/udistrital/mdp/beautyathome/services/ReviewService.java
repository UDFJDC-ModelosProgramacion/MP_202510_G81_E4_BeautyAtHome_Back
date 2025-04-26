package co.edu.udistrital.mdp.beautyathome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udistrital.mdp.beautyathome.entities.ReviewEntity;
import co.edu.udistrital.mdp.beautyathome.repositories.ReviewRepository;

@Service

public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Método que se encarga de pedirle a ReviewRepository que guarda una review en la base de datos
     * @param review a almacenar 
     * @return la ReviewEntity guardada en la base de datos
     */
    @Transactional
    public ReviewEntity createReview(ReviewEntity review){
        if (review.getClient() == null) {
            throw new IllegalArgumentException("The client cannot be null");
        }
        if (review.getService() == null) {
            throw new IllegalArgumentException("The service cannot be null");
        }
        if (review.getOpinion() == null || review.getOpinion().isBlank()) {
            throw new IllegalArgumentException("The opinion cannot be null or empty");
        }
        if (review.getStars() < 1 || review.getStars() > 5) {
            throw new IllegalArgumentException("The number of stars must be between 1 and 5");
        }
        return reviewRepository.save(review);
    }

    @Transactional
    public List<ReviewEntity> getReviews() {
        return reviewRepository.findAll();
    }

    @Transactional
    public ReviewEntity getReview(Long reviewId){
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
        if(reviewEntity.isPresent()){
            return reviewEntity.get();
        }
        else{
            return null;
        }
    }

    @Transactional
    public ReviewEntity updateReview(Long reviewId, ReviewEntity review) {
        Optional<ReviewEntity> optional = reviewRepository.findById(reviewId);
        if (optional.isPresent()) {
            ReviewEntity existingReview = optional.get();
            existingReview.setOpinion(review.getOpinion());
            existingReview.setStars(review.getStars());
            return reviewRepository.save(existingReview);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteReview(Long reviewId) {
        Optional<ReviewEntity> optional = reviewRepository.findById(reviewId);
        if (optional.isPresent()) {
            reviewRepository.deleteById(reviewId);
            return true;
        } else {
            return false;
        }
    }
}
