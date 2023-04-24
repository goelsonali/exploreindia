package com.explore.ec.web;

import com.explore.ec.domain.Tour;
import com.explore.ec.domain.TourRating;
import com.explore.ec.domain.TourRatingPk;
import com.explore.ec.repository.TourRatingRepository;
import com.explore.ec.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Controller for all the tour rating related requests.
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    protected TourRatingController() {

    }

    /**
     * Checks if the tour exists with the id provided.
     * @param tourId id of the tour
     * @return a tour
     * @throws NoSuchElementException if no tour found
     */
    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(()->
            new NoSuchElementException("Tour doesn't exist" + tourId)
        );
    }

    /**
     * To verify if the tour rating exists with the combination of the tourId and customerId.
     * @param tourId id of the tour
     * @param customerId customer id
     * @return tour rating object
     * @throws NoSuchElementException if no rating exists matching tourId and customerId
     */
    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException("Tour rating by this customer doesn't exist"));
    }

    /**
     * Request to get all the rating information for the tour.
     * @param tourId id of the tour
     * @return all the ratings information of the tour
     */
    @GetMapping
    public List<RatingDTO> getAllRatings(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        List<RatingDTO> result = tourRatingRepository.findByPkTourId(tourId).stream()
                .map(RatingDTO::new).collect(Collectors.toList());
        return result;
    }

    /**
     * Get the average score of the ratings for the tour requested.
     * @param tourId id for the tour
     * @return average rating of the tour
     */
    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByPkTourId(tourId).stream()
                .mapToInt(TourRating::score)
                .average().orElseThrow(() -> new NoSuchElementException("Tour has no rating")));
    }

    /**
     * To persist/save the rating information for the tour.
     * @param tourId id for the tour
     * @param ratingDTO rating fields to save
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId,
                                 @RequestBody @Validated RatingDTO ratingDTO) {
        Tour tour = verifyTour(tourId);
        tourRatingRepository
                .save(new TourRating(new TourRatingPk(tour, ratingDTO.customerId()),ratingDTO.score(), ratingDTO.comment()));
    }

    /**
     * This is to update all the existing rating information with the provided json object.
     * Semantics of PUT is to update all the attributes
     * @param tourId id of the tour
     * @param ratingDTO rating fields to update
     * @return all the ratings information updated in the JPA
     */
    @PutMapping
    public RatingDTO updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDTO ratingDTO) {
        TourRating rating = verifyTourRating(tourId, ratingDTO.customerId());
        rating.setScore(ratingDTO.score());
        rating.setComment(ratingDTO.comment());
        return new RatingDTO(tourRatingRepository.save(rating));
    }

    /**
     * Return the updated fields of the rating.
     * Semantics of PATCH is to update only specific attribute/methods of the entity.
     * @param tourId id of the tour
     * @param ratingDTO rating fields to update
     * @return all the ratings information updated in the JPA
     */
    @PatchMapping
    public RatingDTO updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDTO ratingDTO) {
        TourRating rating = verifyTourRating(tourId, ratingDTO.customerId());
        if(ratingDTO.score() != null) {
            rating.setScore(ratingDTO.score());
        }
        if(ratingDTO.comment() != null) {
            rating.setComment(ratingDTO.comment());
        }
        return new RatingDTO(tourRatingRepository.save(rating));
    }

    /**
     * To delete the rating information for the tour by a customer.
     * @param tourId id of the tour
     * @param customerId id of the customer
     */
    @DeleteMapping(path = "/{customerId}")
    public void deleteRating(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId ) {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    /**
     * Returns a 404 if no such element exception.
     * @param elementException exception
     * @return exception message
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException elementException) {
        return elementException.getMessage();
    }
}
