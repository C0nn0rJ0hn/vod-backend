package com.myapp.vod_backend.repository;

import com.myapp.vod_backend.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository  extends CrudRepository<User, Integer> {

    @Override
    User save(User user);

    @Override
    List<User> findAll();

    @Override
    Optional<User> findById(Integer userId);

    void deleteAll();

    List<User> findByName(String userName);

    List<User> findByLastname(String userLastname);

    List<User> findByNameAndLastname(String userName, String userLastname);
}
