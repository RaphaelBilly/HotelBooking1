package fr.efrei.factory;
import fr.efrei.domain.Guest;
import java.time.LocalDate;

public class GuestFactory {
    public static Guest createGuest(long id, String firstName, String lastName,
                                    String email, String phone, String address,
                                    String nationality, String passportNumber,
                                    LocalDate dateOfBirth) {
        if (id <= 0)
            return null;


        //compléter les vérifications + faire le helper + pom xml

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
