package com.myapp.vod_backend.repository;

import com.myapp.vod_backend.domain.Buy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BuyRepository extends CrudRepository<Buy, Integer> {
    @Override
    Buy save(Buy buy);

    @Override
    Optional<Buy> findById(Integer buyId);

    @Override
    List<Buy> findAll();

    @Override
    void deleteById(Integer buyId);
}
