package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.Account;
import com.myapp.vod_backend.domain.User;
import com.myapp.vod_backend.repository.AccountRepository;
import com.myapp.vod_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    public User save(final User user) {
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(final Integer userId) {
        return userRepository.findById(userId);
    }

    public void deleteUser(final Integer userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsersByName(final String userName) {
        return userRepository.findByName(userName);
    }

    public List<User> getUsersByLastname(final String userLastname) {
        return userRepository.findByLastname(userLastname);
    }

    public List<User> getUsersByNameAndLastname(final String userName, final String userLastname) {
        return userRepository.findByNameAndLastname(userName, userLastname);
    }

    public Optional<User> getUserByAccountId(final Integer accountId) {
        return accountRepository.findById(accountId).map(Account::getUser);
    }

    public Account createUserWithAccount(final User user, String country, String language) {
        User savedUser = userRepository.save(user);
        Account account = new Account(savedUser);
        account.setCountry(country);
        account.setLanguage(language);
        account.setPassword(accountService.generateFirstRandomPassword());
        return accountService.create(account);
    }
}
