package fr.efrei.domain;

import java.util.List;

public class Suite extends Room{
    private int nbRooms;
    private int nbGuests;

    public Suite(Builder builder){}

    public static class Builder{
        private int roomNumber;
        private double pricePerNight;
        private int nbRooms;
        private int nbGuests;
        private List<Reservation> reservations;

        public Builder setRoomNumber(int roomNumber){
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder setPricePerNight(double pricePerNight){
            this.pricePerNight = pricePerNight;
            return this;
        }

        public Builder setNbRooms(int nbRooms){
            this.nbRooms = nbRooms;
            return this;
        }

        public Builder setNbGuests(int nbGuests){
            this.nbGuests = nbGuests;
            return this;
        }

        public Builder setReservations(List<Reservation> reservations){
            this.reservations = reservations;
            return this;
        }

        public Builder copy(Suite suite){
            this.roomNumber = suite.roomNumber;
            this.pricePerNight = suite.pricePerNight;
            this.nbRooms = suite.nbRooms;
            this.nbGuests = suite.nbGuests;
            this.reservations = suite.reservations;
            return this;
        }

        public Suite build(){
            return new Suite(this);
        }
    }
}
