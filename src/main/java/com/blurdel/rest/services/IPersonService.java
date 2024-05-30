package com.blurdel.rest.services;

import com.blurdel.rest.model.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService {

    List<Person> getAll();

    Optional<Person> get(Long id);

    Optional<Person> add(Person person);

    Optional<Person> update(Person person);

    Optional<Person> delete(Long id);

}
