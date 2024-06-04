package com.blurdel.rest.repositories;

import com.blurdel.rest.model.Person;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class MemoryDataStore implements IDataStore {

    private Map<Long, Person> mMap = new HashMap<>();
    private static Long id = 0L;


    @Override
    public List<Person> getAll() {
        return new ArrayList<Person>(mMap.values());
    }

    @Override
    public Optional<Person> get(final Long id) {
        Person found = mMap.values().stream()
                .filter(p -> id.equals(p.id()))
                .findFirst().orElse(null);
        return Optional.ofNullable(found);
    }

    @Override
    public Optional<Person> add(final Person person) {
        Person added = new Person(++id, person.name(), person.age());
        mMap.put(added.id(), added);
        return Optional.of(added);
    }

    @Override
    public Optional<Person> update(final Person person) {
        if (mMap.containsKey(person.id())) {
            mMap.remove(person.id());
            mMap.put(person.id(), person);
            return Optional.of(person);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Person> delete(final Long id) {
        Person deleted = mMap.remove(id);
        return Optional.ofNullable(deleted);
    }

}
