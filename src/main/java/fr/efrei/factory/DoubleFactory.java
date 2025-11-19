package fr.efrei.factory;

import fr.efrei.domain.Double;
import fr.efrei.domain.Reservation;
import java.util.List;

public class DoubleFactory {
    public static Double createDouble(int roomNumber, double pricePerNight, String bedType, List<Reservation> reservations){
        if(roomNumber < 0) return null;
        if(pricePerNight < 0.0) return null;
        if(bedType == null || bedType.isEmpty()) return null;
        if(reservations == null) return null;

        return new Double.Builder()
                .setRoomNumber(roomNumber)
                .setPricePerNight(pricePerNight)
                .setBedType(bedType)
                .setReservations(reservations)
                .build();
    }
}
