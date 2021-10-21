package com.myapp.vod_backend.repository;

import com.myapp.vod_backend.domain.WatchList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface WatchListRepository extends CrudRepository<WatchList, Integer> {

    @Override
    WatchList save(WatchList watchList);

    @Override
    List<WatchList> findAll();

    @Override
    Optional<WatchList> findById(Integer watchListId);

    void deleteById(Integer watchlistId);
}
