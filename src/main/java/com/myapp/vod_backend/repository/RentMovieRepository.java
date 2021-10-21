package com.myapp.vod_backend.repository;

import com.myapp.vod_backend.domain.RentMovie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RentMovieRepository extends CrudRepository<RentMovie, Integer> {

    @Override
    List<RentMovie> findAll();

    @Override
    Optional<RentMovie> findById(Integer rentId);

    @Override
    RentMovie save(RentMovie rentMovie);

    @Override
    void deleteById(Integer rentMovieId);
}
