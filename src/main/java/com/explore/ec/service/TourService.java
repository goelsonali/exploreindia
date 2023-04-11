package com.explore.ec.service;

import com.explore.ec.domain.Difficulty;
import com.explore.ec.domain.Region;
import com.explore.ec.domain.Tour;
import com.explore.ec.domain.TourPackage;
import com.explore.ec.repository.TourPackageRepository;
import com.explore.ec.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the Tour
 */
@Service
public class TourService {

    private TourRepository tourRepository;
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }


    /**
     * To create a tour or return an exsiting one.
     * @param title of the tour
     * @param description of the tour
     * @param blurb if any
     * @param price of the tour
     * @param duration of the tour
     * @param bullets if any
     * @param keywords for other use
     * @param tourPackageName package
     * @param difficulty of the tour
     * @param region of the tour
     * @return Tour Entity
     */
    public Tour createTour(String title, String description, String blurb, Integer price, String duration, String bullets,
                           String keywords, String tourPackageName, Difficulty difficulty, Region region) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("TourPackage doesn't exist" + tourPackageName));
        return tourRepository.save(new Tour(title,description,blurb,price,duration,bullets,keywords,tourPackage,difficulty,region));

    }

    /**
     * Get total no. of tours.
     * @return count
     */
    public long total() {
        return tourRepository.count();
    }
}
