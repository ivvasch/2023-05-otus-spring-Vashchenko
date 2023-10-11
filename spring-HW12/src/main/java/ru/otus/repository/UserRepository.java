package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import ru.otus.model.User;

@Component
public interface UserRepository extends MongoRepository<User, String> {
}
