package fr.efrei.repository;

import fr.efrei.domain.Single;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleRepository implements ISingleRepository {
    private Map<Integer, Single> singles = new HashMap<>();
    private static ISingleRepository repository = null;

    private SingleRepository() {
        singles = new HashMap<>();
    }

    public static ISingleRepository getRepository() {
        if (repository == null) {
            repository = new SingleRepository();
        }
        return repository;
    }


    @Override
    public Single create(Single single) {
        if (single == null) return null;
        singles.put(single.getRoomNumber(), single);
        return single;
    }

    @Override
    public Single read(Integer id) {
        return singles.get(id);
    }

    @Override
    public Single update(Single single) {
        if (single == null) return null;
        singles.put(single.getRoomNumber(), single);
        return single;
    }

    @Override
    public boolean delete(Integer id) {
        return singles.remove(id) != null;
    }

    @Override
    public List<Single> getAll() {
        return new ArrayList<>(singles.values());
    }
}