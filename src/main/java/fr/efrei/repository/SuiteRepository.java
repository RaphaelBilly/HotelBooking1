package fr.efrei.repository;

import fr.efrei.domain.Suite;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuiteRepository implements ISuiteRepository {
    private Map<Integer, Suite> suites = new HashMap<>();
    private static ISuiteRepository repository = null;

    private SuiteRepository() {
        suites = new HashMap<>();
    }

    public static ISuiteRepository getRepository() {
        if (repository == null) {
            repository = new SuiteRepository();
        }
        return repository;
    }


    @Override
    public Suite create(Suite suite) {
        if (suite == null) return null;
        suites.put(suite.getRoomNumber(), suite);
        return suite;
    }

    @Override
    public Suite read(Integer id) {
        return suites.get(id);
    }

    @Override
    public Suite update(Suite suite) {
        if (suite == null) return null;
        suites.put(suite.getRoomNumber(), suite);
        return suite;
    }

    @Override
    public boolean delete(Integer id) {
        return suites.remove(id) != null;
    }

    @Override
    public List<Suite> getAll() {
        return new ArrayList<>(suites.values());
    }
}