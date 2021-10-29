package ru.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.auth.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
