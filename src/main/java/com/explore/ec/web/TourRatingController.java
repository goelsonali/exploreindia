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

import java.util.NoSuchElementException;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId,
                                 @RequestBody @Validated RatingDTO ratingDTO) {
        Tour tour = verifyTour(tourId);
        tourRatingRepository
                .save(new TourRating(new TourRatingPk(tour, ratingDTO.customerId()),ratingDTO.score(), ratingDTO.comment()));
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
