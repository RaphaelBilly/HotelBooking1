package fr.efrei.repository;

import fr.efrei.domain.Reservation;

import java.util.List;

public interface IReservationRepository extends IRepository<Reservation, Integer> {
    //Reservation read(int id);
    //boolean delete(int id); //Fixed error the 01/12/2025. Functions are in x2 with the IRepository.

    List<Reservation> getAll();

}
