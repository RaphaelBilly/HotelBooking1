package fr.efrei.domain;

import java.util.List;

public abstract class Room {
    protected int roomNumber;
    protected double pricePerNight;
    protected List<Reservation> reservations;
}
