package fr.efrei.factory;

import fr.efrei.domain.Single;
import fr.efrei.domain.Reservation;
import java.util.List;

public class SingleFactory {
    public static Single createSingle(int roomNumber, double pricePerNight, String bedType, List<Reservation> reservations){
        if(roomNumber < 0) return null;
        if(pricePerNight < 0.0) return null;
        if(bedType == null || bedType.isEmpty()) return null;
        if(reservations == null) return null;

        return new Single.Builder()
                .setRoomNumber(roomNumber)
                .setPricePerNight(pricePerNight)
                .setBedType(bedType)
                .setReservations(reservations)
                .build();
    }
}
