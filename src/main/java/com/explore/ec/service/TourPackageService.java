package com.explore.ec.service;

import com.explore.ec.domain.TourPackage;
import com.explore.ec.repository.TourPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourPackageService {

    private TourPackageRepository tourPackageRepository;


    @Autowired
    public TourPackageService(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    /**
     * To create or fetch existing package in DB.
     * @param code code of the package
     * @param name name of the package
     * @return new or existing package
     */
    public TourPackage createTourPackage(String code, String name) {


        return tourPackageRepository.findById(code)
                .orElse(tourPackageRepository.save(new TourPackage(code,name)));
    }

    /**
     * Get all the packages in the DB.
     * @return all the tour packages
     */
    public Iterable<TourPackage> lookup() {
        return tourPackageRepository.findAll();
    }

    /**
     * Get count of the packages.
     * @return total no. of packages
     */
    public long total() {
        return tourPackageRepository.count();
    }
}
