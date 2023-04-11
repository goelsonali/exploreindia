package com.explore.ec.repository;

import com.explore.ec.domain.TourRating;
import com.explore.ec.domain.TourRatingPk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;


/**
 * Tour Rating Repository, that can stores the rating information for tours.
 */
@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk> {

    /**
     * To find the tour rating information based on tour Id provided
     * @param tourId id
     * @return all the rating
     */
    List<TourRating> findByPkTourId(Integer tourId);

    /**
     * To find the tour rating information based on tour and customer
     * @param tourId id
     * @param customerId id
     * @return tour rated by the customer
     */
    Optional<TourRating> findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);



}
