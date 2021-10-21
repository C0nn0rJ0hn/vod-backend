package com.myapp.vod_backend.repository;

import com.myapp.vod_backend.domain.RentTvShow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RentTvShowRepository  extends CrudRepository<RentTvShow, Integer> {

    @Override
    List<RentTvShow> findAll();

    @Override
    Optional<RentTvShow> findById(Integer rentTvShowId);

    @Override
    void deleteById(Integer rentTvShowId);

    @Override
    RentTvShow save(RentTvShow rentTvShow);
}
