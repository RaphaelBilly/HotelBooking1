package fr.efrei;

import fr.efrei.domain.Guest;
import fr.efrei.domain.Room;
import fr.efrei.domain.Single;
import fr.efrei.domain.Suite;
import fr.efrei.domain.Reservation;
import fr.efrei.domain.DoubleRoom;

import fr.efrei.factory.GuestFactory;
import fr.efrei.factory.SingleFactory;
import fr.efrei.factory.DoubleFactory;
import fr.efrei.factory.SuiteFactory;
import fr.efrei.factory.ReservationFactory;

import fr.efrei.repository.GuestRepository;
import fr.efrei.repository.IGuestRepository;
import fr.efrei.repository.SingleRepository;
import fr.efrei.repository.ISingleRepository;
import fr.efrei.repository.DoubleRepository;
import fr.efrei.repository.IDoubleRepository;
import fr.efrei.repository.SuiteRepository;
import fr.efrei.repository.ISuiteRepository;
import fr.efrei.repository.ReservationRepository;
import fr.efrei.repository.IReservationRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        System.out.println("*\n");
        System.out.println("  WELCOME TO HOTEL MANAGEMENT SYSTEM");
        System.out.println("*\n");
        System.out.println("Manager: Raphaël Billy (ID: 251760790)");
        System.out.println();

        // Initialize demo data
        initializeDemoData();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Your choice: ");

            switch (choice) {
                case 1:
                    registerNewGuest();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    checkOut();
                    break;
                case 4:
                    viewHotelStatus();
                    break;
                case 5:
                    searchGuests();
                    break;
                case 6:
                    manageRooms();
                    break;
                case 7:
                    displayRoomCalendar();
                    break;
                case 8:
                    checkRoomAvailabilityForDates();
                    break;
                case 9:
                    System.out.println("\nThank you for using Hotel Management System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n-   MAIN MENU   -");
        System.out.println("1. Register new guest");
        System.out.println("2. Make a reservation");
        System.out.println("3. Check-out guest");
        System.out.println("4. View hotel status");
        System.out.println("5. Search guests");
        System.out.println("6. Manage rooms");
        System.out.println("7. View room calendars");
        System.out.println("8. Check room availability for dates");
        System.out.println("9. Exit");
    }

    private static void initializeDemoData() {
        System.out.println("Initializing demo data...");

        Single single1 = SingleFactory.createSingle(101, 89.99, "Queen", new ArrayList<>());
        Single single2 = SingleFactory.createSingle(102, 79.99, "Double", new ArrayList<>());

        DoubleRoom double1 = DoubleFactory.createDouble(201, 129.99, "Two Queens", new ArrayList<>());
        DoubleRoom double2 = DoubleFactory.createDouble(202, 139.99, "King", new ArrayList<>());

        Suite suite1 = SuiteFactory.createSuite(301, 249.99, 2, 4, new ArrayList<>());

        if (single1 != null) SingleRepository.getRepository().create(single1);
        if (single2 != null) SingleRepository.getRepository().create(single2);
        if (double1 != null) DoubleRepository.getRepository().create(double1);
        if (double2 != null) DoubleRepository.getRepository().create(double2);
        if (suite1 != null) SuiteRepository.getRepository().create(suite1);

        Guest demoGuest = GuestFactory.createGuest(
                "Kruben", "Naidoo", "kruben.naido@cput.sa", "0123456789",
                "South African", "AB123456", LocalDate.of(1985, 5, 15)
        );
        if (demoGuest != null) {
            GuestRepository.getRepository().create(demoGuest);
        }

        System.out.println("Demo data initialized successfully!");
        System.out.println("- 2 Single rooms (101, 102)");
        System.out.println("- 2 Double rooms (201, 202)");
        System.out.println("- 1 Suite (301)");
        System.out.println("- 1 Demo guest (Kruben Naidoo)");
    }

    private static void registerNewGuest() {
        System.out.println("\n-   NEW GUEST REGISTRATION   -");

        System.out.print("First name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Nationality: ");
        String nationality = scanner.nextLine();

        System.out.print("Passport number: ");
        String passport = scanner.nextLine();

        System.out.print("Date of birth (YYYY-MM-DD): ");
        String dobStr = scanner.nextLine();

        LocalDate dateOfBirth;
        try {
            dateOfBirth = LocalDate.parse(dobStr);
        } catch (Exception e) {
            System.out.println("Invalid date format!");
            return;
        }

        // Create guest
        Guest guest = GuestFactory.createGuest(firstName, lastName, email, phone,
                nationality, passport, dateOfBirth);

        if (guest != null) {
            IGuestRepository guestRepo = GuestRepository.getRepository();
            Guest savedGuest = guestRepo.create(guest);
            if (savedGuest != null) {
                System.out.println("\nSUCCESS: Guest registered!");
                System.out.println("Guest ID: " + savedGuest.getId());
                System.out.println("Name: " + savedGuest.getFirstName() + " " + savedGuest.getLastName());

                // Ask for reservation
                System.out.print("\nWould you like to make a reservation now? (yes/no): ");
                String response = scanner.nextLine().toLowerCase();
                if (response.equals("yes") || response.equals("y")) {
                    makeReservationForGuest(savedGuest);
                }
            } else {
                System.out.println("ERROR: Failed to save guest!");
            }
        } else {
            System.out.println("ERROR: Invalid guest data! Please check:");
            System.out.println("- Email format");
            System.out.println("- Phone format (international +XX or 10 digits)");
            System.out.println("- Passport (6-12 alphanumeric characters)");
            System.out.println("- Age (must be 18+)");
        }
    }

    private static void makeReservation() {
        System.out.println("\n NEW RESERVATION ");

        // List all guests
        IGuestRepository guestRepo = GuestRepository.getRepository();
        List<Guest> guests = guestRepo.getAll();
        if (guests.isEmpty()) {
            System.out.println("No guests registered yet. Please register a guest first.");
            return;
        }

        System.out.println("Registered guests:");
        for (int i = 0; i < guests.size(); i++) {
            Guest g = guests.get(i);
            System.out.println((i+1) + ". " + g.getFirstName() + " " + g.getLastName() +
                    " (ID: " + g.getId() + ")");
        }

        System.out.print("\nSelect guest by number: ");
        int guestIndex = getIntInput("") - 1;

        if (guestIndex < 0 || guestIndex >= guests.size()) {
            System.out.println("Invalid selection!");
            return;
        }

        Guest selectedGuest = guests.get(guestIndex);
        makeReservationForGuest(selectedGuest);
    }

    private static void makeReservationForGuest(Guest guest) {
        System.out.println("\n RESERVATION FOR " + guest.getFirstName() + " " + guest.getLastName() + " ===");

        // Demander les dates d'abord
        System.out.print("Arrival date (YYYY-MM-DD): ");
        String arrivalStr = scanner.nextLine();

        System.out.print("Departure date (YYYY-MM-DD): ");
        String departureStr = scanner.nextLine();

        Date arrival, departure;
        try {
            arrival = dateFormat.parse(arrivalStr);
            departure = dateFormat.parse(departureStr);
        } catch (Exception e) {
            System.out.println("Invalid date format!");
            return;
        }

        // Check if departure is after arrival
        if (!departure.after(arrival)) {
            System.out.println("ERROR: Departure must be after arrival!");
            return;
        }

        // Show available rooms for these dates
        showAvailableRoomsForDates(arrival, departure);

        System.out.print("\nEnter room number: ");
        int roomNumber = getIntInput("");

        // Find room
        Room room = findRoomByNumber(roomNumber);
        if (room == null) {
            System.out.println("ERROR: Room not found!");
            return;
        }

        // Check room availability
        if (!isRoomAvailableForPeriod(room, arrival, departure)) {
            System.out.println("ERROR: Room not available for selected dates!");
            return;
        }

        // Create reservation
        Reservation reservation = ReservationFactory.createReservation(arrival, departure);
        if (reservation != null) {
            IReservationRepository resRepo = ReservationRepository.getRepository();
            Reservation savedReservation = resRepo.create(reservation);
            if (savedReservation != null) {
                // Add reservation to room
                addReservationToRoom(room, savedReservation);

                System.out.println("\nSUCCESS: Reservation created!");
                System.out.println("Reservation ID: " + savedReservation.getReservationId());
                System.out.println("Room: " + room.getRoomNumber());
                System.out.println("Price per night: €" + room.getPricePerNight());

                // Calculate total
                long nights = (departure.getTime() - arrival.getTime()) / (1000 * 60 * 60 * 24);
                double total = nights * room.getPricePerNight();
                System.out.println("Total for " + nights + " nights: €" + total);

                // Ask to view calendar
                System.out.print("\nView room calendar? (yes/no): ");
                String viewCalendar = scanner.nextLine().toLowerCase();
                if (viewCalendar.equals("yes") || viewCalendar.equals("y")) {
                    displaySingleRoomCalendar(room);
                }
            }
        }
    }
}//to delete

