package com.app.librarium.repository;

import com.app.librarium.model.Book;
import com.app.librarium.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    List<User> findByEmailLike(String email);
}
