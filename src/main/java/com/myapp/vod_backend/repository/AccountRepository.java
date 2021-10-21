package com.myapp.vod_backend.repository;

import com.myapp.vod_backend.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Override
    Account save(Account account);

    @Override
    List<Account> findAll();

    @Override
    Optional<Account> findById(Integer accountId);

    void deleteById(Integer accountId);
}
