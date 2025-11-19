package fr.efrei.domain;

import java.util.List;

public class Double extends Room{
    private String bedType;

    public Double(Builder builder){}

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

        public Builder copy(Double doubleRoom){
            this.roomNumber = doubleRoom.roomNumber;
            this.pricePerNight = doubleRoom.pricePerNight;
            this.bedType = doubleRoom.bedType;
            this.reservations = doubleRoom.reservations;
            return this;
        }

        public Double build(){
            return new Double(this);
        }
    }
}
