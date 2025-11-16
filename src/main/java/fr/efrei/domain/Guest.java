package fr.efrei.domain;

import java.time.LocalDate;

public class Guest {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String nationality;
    private String passportNumber;
    private LocalDate dateOfBirth;

    private Guest(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.nationality = builder.nationality;
        this.passportNumber = builder.passportNumber;
        this.dateOfBirth = builder.dateOfBirth;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", nationality='" + nationality + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    public static class Builder {
        private long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String nationality;
        private String passportNumber;
        private LocalDate dateOfBirth;



        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setNationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public Builder setPassportNumber(String passportNumber) {
            this.passportNumber = passportNumber;
            return this;
        }

        public Builder setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }


        public Builder copy(Guest guest) {
            this.id = guest.id;
            this.firstName = guest.firstName;
            this.lastName = guest.lastName;
            this.email = guest.email;
            this.phone = guest.phone;
            this.nationality = guest.nationality;
            this.passportNumber = guest.passportNumber;
            this.dateOfBirth = guest.dateOfBirth;
            return this;
        }

        public Guest build() {
            return new Guest(this);
        }
    }
}
