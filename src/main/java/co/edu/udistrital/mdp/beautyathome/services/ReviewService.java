package co.edu.udistrital.mdp.beautyathome.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import co.edu.udistrital.mdp.beautyathome.entities.ReviewEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Método que se encarga de pedirle a ReviewRepository que guarda una review en la base de datos.
     * @param review a almacenar 
     * @return la ReviewEntity guardada en la base de datos.
     * @throws IllegalOperationException si algún campo es nulo o vacío
     */
    @Transactional
    public ReviewEntity createReview(ReviewEntity review) throws IllegalOperationException{
        if(review.getService() == null){
            throw new IllegalOperationException("The service is not valid");
        }
        if(review.getClient() == null){
            throw new IllegalOperationException("The client is not valid");
        }
        if(review.getOpinion() == null || review.getOpinion().isEmpty()){
            throw new IllegalOperationException("The opinion is not valid");
        }
        if(review.getStars() < 1 || review.getStars()>5){
            throw new IllegalOperationException("The number of stars is not valid");
        }
        return reviewRepository.save(review);
    }

    /**
     * Método que se encarga de obtener todas las ReviewEntity de la base de datos
     * @return La lista de reseñas encontradas.
     */
    @Transactional
    public List<ReviewEntity> getReviews() {
        return reviewRepository.findAll();
    }

    /**
     * Método que se encarga de obtener un ReviewEntity de la base da deatos mediante su id.
     * @param reviewId id de la ReviewEntity a buscar.
     * @return La ReviewEntity correspondiente. 
     * @throws EntityNotFoundException en caso de no encontrar la reseña.
     */
    @Transactional
    public ReviewEntity getReview(Long reviewId){
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
        return reviewEntity.orElseThrow(()-> new EntityNotFoundException());
    }

    /**
     * Método que se encarga de actualizar una reseña en la base de datos.
     * @param reviewId id de la reseña a actualizar.
     * @param review reseña que contiene los datos a actualizar.
     * @return La reseña actualizada.
     * @throws IllegalOperationException si algún campo es nulo o vacío.
     * @throws EntityNotFoundException si no se encuentra la reseña a actualizar.
     */
    @Transactional
    public ReviewEntity updateReview(Long reviewId, ReviewEntity review) throws IllegalOperationException {
        //Busca la ReviewEntity a modificar
        Optional<ReviewEntity> optionalReviewEntity = reviewRepository.findById(reviewId);
        //Si el Optional<ReviewEntity> está vacío
        optionalReviewEntity.orElseThrow(() -> new EntityNotFoundException("The review with id: " + reviewId + " was not found"));
        review.setId(reviewId);
        if(review.getService() == null){
            throw new IllegalOperationException("The service is not valid");
        }
        if(review.getClient() == null){
            throw new IllegalOperationException("The client is not valid");
        }
        if(review.getOpinion() == null || review.getOpinion().isEmpty()){
            throw new IllegalOperationException("The opinion is not valid");
        }
        if(review.getStars() < 1 || review.getStars()>5){
            throw new IllegalOperationException("The number of stars is not valid");
        }
        return reviewRepository.save(review);
    }

    /**
     * Método que se encarga de eliminar una reseña por su ID.
     * @param reviewId ID de la ReviewEntity a eliminar.
     * @throws EntityNotFoundException si no se encuentra la reseña.
     */
    @Transactional
    public void deleteReview(Long reviewId) {
        Optional<ReviewEntity> optionalReviewEntity = reviewRepository.findById(reviewId);
        optionalReviewEntity.orElseThrow(()-> new EntityNotFoundException("The review with id: "
        + reviewId + " couldn't be removed because it wasn't found."));
        reviewRepository.deleteById(reviewId);
    }

}
