package fr.efrei.domain;

import java.util.List;

public class Single extends Room{
    private String bedType;

    public Single(Builder builder){}

    public static class Builder{
        private int roomNumber;
        private double pricePerNight;
        private String bedType;
        private List<Reservation> reservations;

        public Builder setRoomNumber(int roomNumber){
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder setPricePerNight(double pricePerNight){
            this.pricePerNight = pricePerNight;
            return this;
        }

        public Builder setBedType(String bedType){
            this.bedType = bedType;
            return this;
        }

        public Builder setReservations(List<Reservation> reservations){
            this.reservations = reservations;
            return this;
        }

        public Builder copy(Single single){
            this.roomNumber = single.roomNumber;
            this.pricePerNight = single.pricePerNight;
            this.bedType = single.bedType;
            this.reservations = single.reservations;
            return this;
        }

        public Single build(){
            return new Single(this);
        }
    }
}
