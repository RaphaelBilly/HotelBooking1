package fr.efrei.factory;

import fr.efrei.domain.Reservation;
import fr.efrei.domain.Suite;
import java.util.List;

public class SuiteFactory {
    public static Suite createSuite(int roomNumber, double pricePerNight, int nbRooms, int nbGuests, List<Reservation> reservations){
        if(roomNumber < 0) return null;
        if(pricePerNight < 0.0) return null;
        if(nbRooms <= 0) return null;
        if(nbGuests <= 0) return null;
        if(reservations == null) return null;

        return new Suite.Builder()
                .setRoomNumber(roomNumber)
                .setPricePerNight(pricePerNight)
                .setNbRooms(nbRooms)
                .setNbGuests(nbGuests)
                .setReservations(reservations)
                .build();
    }
}
