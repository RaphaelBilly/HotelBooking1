package fr.efrei.repository;

import fr.efrei.domain.Reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationRepository implements IReservationRepository {

    // Singleton
    private static IReservationRepository repository = null;
    //private List<Reservation> reservationList; //I remove List to get a better complexity with hashMap
    private Map<Integer, Reservation> reservationMap;

    private ReservationRepository() {
        reservationMap = new HashMap<>();
    }

    public static IReservationRepository getRepository() {
        if (repository == null) {
            repository = new ReservationRepository();
        }
        return repository;
    }

    /*@Override //WITH LIST
    public Reservation create(Reservation reservation) {
        if (reservation == null)
            return null;

        boolean success = reservationList.add(reservation);
        return success ? reservation : null;
    }*/

    //with map
    @Override
    public Reservation create(Reservation reservation) {
        if (reservation == null)
            return null;

        if (reservationMap.containsKey(reservation.getReservationId())) {
            return null;
        }

        reservationMap.put(reservation.getReservationId(), reservation);
        return reservation;
    }

    /*@Override
    public Reservation read(Integer integer) {
        return null;
    }

    @Override
    public Reservation read(int id) {
        for (Reservation r : reservationList) {
            if (r.getReservationId() == id) {
                return r;
            }
        }
        return null;
    }*/

    @Override
    public Reservation read(Integer id) {
        return reservationMap.get(id);  //better complexity O(1) instead of O(n)
    }



    @Override
    public Reservation update(Reservation reservation) {
        if (reservation == null)
            return null;

        if (!reservationMap.containsKey(reservation.getReservationId())) {
            return null;
        }

        reservationMap.put(reservation.getReservationId(), reservation);
        return reservation;
    }

    @Override
    public boolean delete(Integer id) {
        return reservationMap.remove(id) != null;
    }

    @Override
    public List<Reservation> getAll() {
        return new ArrayList<>(reservationMap.values());
    }
}
