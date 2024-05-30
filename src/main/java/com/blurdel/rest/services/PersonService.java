package com.blurdel.rest.services;

import com.blurdel.rest.model.Person;
import com.blurdel.rest.repositories.IDataStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class PersonService implements IPersonService {

    @Inject
    private IDataStore dataStore;


    @Override
    public List<Person> getAll() {
        return dataStore.getAll();
    }

    @Override
    public Optional<Person> get(final Long id) {
        Objects.requireNonNull(id, "id cannot be null");
        return dataStore.get(id);
    }

    @Override
    public Optional<Person> add(final Person person) {
        Objects.requireNonNull(person, "person cannot be null");
        return dataStore.add(person);
    }

    @Override
    public Optional<Person> update(final Person person) {
        Objects.requireNonNull(person, "person cannot be null");
        return dataStore.update(person);
    }

    @Override
    public Optional<Person> delete(final Long id) {
        Objects.requireNonNull(id, "id cannot be null");
        return dataStore.delete(id);
    }

}
