package com.explore.ec.repository;

import com.explore.ec.domain.Tour;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This the entity repository for tours , this will be a paginated repository
 */
@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {

    /**
     * For the search endpoint to find the tours using package code and with some pagination attributes.
     * eg: http://localhost:8080/tours?size=3&page=0&sort=title,asc
     * @param code of the tour package
     * @param pageable object
     * @return list of tours
     */
    Page<Tour> findByTourPackageCode(String code, Pageable pageable);

    @Override
    @RestResource(exported = false)
    <S extends Tour> List<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    List<Tour> findAll();

    @Override
    @RestResource(exported = false)
    <S extends Tour> S save(S entity);

    @Override
    @RestResource(exported = false)
    void deleteById(Integer integer);

    @Override
    @RestResource(exported = false)
    void delete(Tour entity);

    @Override
    @RestResource(exported = false)
    void deleteAllById(Iterable<? extends Integer> integers);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Tour> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    List<Tour> findAll(Sort sort);

    @Override
    @RestResource(exported = false)
    Page<Tour> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    <S extends Tour> Page<S> findAll(Example<S> example, Pageable pageable);
}
