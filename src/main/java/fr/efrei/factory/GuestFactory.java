package fr.efrei.factory;
import fr.efrei.domain.Guest;
import java.time.LocalDate;

public class GuestFactory {
    public static Guest createGuest(/*long id,*/String firstName, String lastName,
                                    String email, String phone,
                                    String nationality, String passportNumber,
                                    LocalDate dateOfBirth) {

        long id = System.currentTimeMillis() % 1000000;

        if (id <= 0)
            return null;

        if (firstName == null || firstName.trim().isEmpty())
            return null;

        if (lastName == null || lastName.trim().isEmpty())
            return null;

        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            return null;

        if (phone == null || !(phone.matches("^[+][0-9]{1,3}[0-9]{4,14}$") || phone.matches("^[0-9]{10}$")))
            return null;

        if (nationality == null || nationality.trim().isEmpty())
            return null;

        if (passportNumber == null || !passportNumber.matches("^[A-Z0-9]{6,12}$"))
            return null;

        if (dateOfBirth == null || !dateOfBirth.isBefore(LocalDate.now().minusYears(18)))
            return null;

        if (dateOfBirth.isBefore(LocalDate.of(1900, 1, 1)))
            return null;


        return new Guest.Builder().setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPhone(phone)
                .setNationality(nationality)
                .setPassportNumber(passportNumber)
                .setDateOfBirth(dateOfBirth)
                .build();
    }
}
