package com.explore.ec.web;

import com.explore.ec.domain.TourRating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Rating Data transfer object for rating a tour.
 */
public class RatingDTO {

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max =255)
    private String comment;

    @NotNull
    private Integer customerId;

    /**
     * Construct a RatingDto from a fully instantiated TourRating.
     *
     * @param tourRating Tour Rating Object
     */
    public RatingDTO(TourRating tourRating) {
        this(tourRating.score(), tourRating.comment(), tourRating.pk().customerId());
    }
    /**
     * Constructor to fully initialize the RatingDto
     *
     * @param score score 1-5
     * @param comment comment
     * @param customerId customer identifier
     */
    private RatingDTO(Integer score, String comment, Integer customerId) {
        this.score = score;
        this.comment = comment;
        this.customerId = customerId;
    }

    public Integer score() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String comment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer customerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
