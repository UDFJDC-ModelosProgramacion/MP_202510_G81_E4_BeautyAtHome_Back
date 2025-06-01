package co.edu.udistrital.mdp.beautyathome.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.mdp.beautyathome.dto.ReviewDTO;
import co.edu.udistrital.mdp.beautyathome.dto.ReviewDetailDTO;
import co.edu.udistrital.mdp.beautyathome.entities.ReviewEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.ReviewService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Método que utiliza ReviewService para crear una review
     * @param reviewDTO review a crear
     * @return una instancia de ReviewDTO
     * @throws IllegalOperationException
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ReviewDTO create(@RequestBody ReviewDTO reviewDTO) throws IllegalOperationException{
        ReviewEntity reviewEntity = reviewService.createReview(modelMapper.map(reviewDTO, ReviewEntity.class));
        return modelMapper.map(reviewEntity, ReviewDTO.class);
    }

    /**
     * Método que utiliza ReviewService para obtener todas las reviews
     * @return Lista con todas las reviewsDTO
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ReviewDetailDTO> findAll() {
	    List<ReviewEntity> reviews = reviewService.getReviews();
	    return modelMapper.map(reviews, new TypeToken<List<ReviewDetailDTO>>() {
	    }.getType());
    }

    /**
     * Método que utiliza ReviewService para obtener una review
     * @param id de la review a obtener
     * @return una instancia de ReviewDetailDTO
     * @throws EntityNotFoundException
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReviewDetailDTO findOne(@PathVariable Long id) throws EntityNotFoundException{
        ReviewEntity reviewEntity = reviewService.getReview(id);
        return modelMapper.map(reviewEntity, ReviewDetailDTO.class);
    }

    /**
     * Método que utiliza ReviewService para actualizar una review
     * @param id de la review a actualizar
     * @param reviewDTO entidad ReviewDTO actualizada
     * @return entidad ReviewDTO actualizada
     * @throws IllegalOperationException
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReviewDTO update(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) throws IllegalOperationException{
        ReviewEntity reviewEntity = reviewService.updateReview(id, modelMapper.map(reviewDTO, ReviewEntity.class));
        return modelMapper.map(reviewEntity, ReviewDTO.class);
    }

    /**
     * Método que utiliza ReviewService para eliminar una review
     * @param id de la review a eliminar
     * @throws EntityNotFoundException
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException{
        reviewService.deleteReview(id);
    }
}
