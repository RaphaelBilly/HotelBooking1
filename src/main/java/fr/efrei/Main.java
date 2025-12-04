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
        System.out.println("\n RESERVATION FOR " + guest.getFirstName() + " " + guest.getLastName() + "   -");

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

    private static void checkOut() { //
        System.out.println("\n-   CHECK-OUT   -");

        System.out.print("Enter reservation ID: ");
        int reservationId = getIntInput("");

        IReservationRepository resRepo = ReservationRepository.getRepository();
        Reservation reservation = resRepo.read(reservationId);
        if (reservation == null) {
            System.out.println("ERROR: Reservation not found!");
            return;
        }

        System.out.println("Reservation found:");
        System.out.println("- Arrival: " + reservation.getArrival());
        System.out.println("- Departure: " + reservation.getDeparture());

        // Find room
        Room room = findRoomByReservation(reservation);
        if (room != null) {
            System.out.println("- Room: " + room.getRoomNumber());
        }

        System.out.print("\nConfirm check-out? (yes/no): ");
        String confirm = scanner.nextLine().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            // Find and clear room
            if (room != null) {
                removeReservationFromRoom(room, reservation);
            }

            // Delete reservation
            boolean deleted = resRepo.delete(reservationId);
            if (deleted) {
                System.out.println("SUCCESS: Check-out completed!");
                if (room != null) {
                    System.out.println("Room " + room.getRoomNumber() + " is now available.");
                }
            }
        }
    }

    private static void viewHotelStatus() {//
        System.out.println("\n-   HOTEL STATUS   -");

        // Guest statistics
        IGuestRepository guestRepo = GuestRepository.getRepository();
        List<Guest> guests = guestRepo.getAll();
        System.out.println("Total guests: " + guests.size());

        // Reservation statistics
        IReservationRepository resRepo = ReservationRepository.getRepository();
        List<Reservation> reservations = resRepo.getAll();
        System.out.println("Active reservations: " + reservations.size());

        // Room statistics
        ISingleRepository singleRepo = SingleRepository.getRepository();
        IDoubleRepository doubleRepo = DoubleRepository.getRepository();
        ISuiteRepository suiteRepo = SuiteRepository.getRepository();

        List<Single> singles = singleRepo.getAll();
        List<DoubleRoom> doubleRooms = doubleRepo.getAll();
        List<Suite> suites = suiteRepo.getAll();

        System.out.println("\n-   ROOMS   -");
        System.out.println("Single rooms: " + singles.size());
        System.out.println("Double rooms: " + doubleRooms.size());
        System.out.println("Suites: " + suites.size());
        System.out.println("Total rooms: " + (singles.size() + doubleRooms.size() + suites.size()));

        // Rooms available today
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        int availableToday = countAvailableRooms(today, tomorrow);
        System.out.println("Rooms available for check-in today: " + availableToday);

        // Recent guests
        if (!guests.isEmpty()) {
            System.out.println("\n-   RECENT GUESTS   -");
            int limit = Math.min(3, guests.size());
            for (int i = 0; i < limit; i++) {
                Guest g = guests.get(i);
                System.out.println("- " + g.getFirstName() + " " + g.getLastName() +
                        " (" + g.getNationality() + ") - ID: " + g.getId());
            }
        }
    }

    private static void searchGuests() {//
        System.out.println("\nSEARCH GUESTS");
        System.out.println("1. Search by last name");
        System.out.println("2. Search by nationality");
        System.out.println("3. View all guests");
        System.out.println("4. Search by passport number");

        int choice = getIntInput("Your choice: ");

        IGuestRepository guestRepo = GuestRepository.getRepository();
        List<Guest> guests = guestRepo.getAll();
        List<Guest> results = new ArrayList<>();

        switch (choice) {
            case 1:
                System.out.print("Enter last name: ");
                String lastName = scanner.nextLine();
                for (Guest g : guests) {
                    if (g.getLastName().toLowerCase().contains(lastName.toLowerCase())) {
                        results.add(g);
                    }
                }
                break;
            case 2:
                System.out.print("Enter nationality: ");
                String nationality = scanner.nextLine();
                for (Guest g : guests) {
                    if (g.getNationality().equalsIgnoreCase(nationality)) {
                        results.add(g);
                    }
                }
                break;
            case 3:
                results = guests;
                break;
            case 4:
                System.out.print("Enter passport number: ");
                String passport = scanner.nextLine();
                for (Guest g : guests) {
                    if (g.getPassportNumber().equalsIgnoreCase(passport)) {
                        results.add(g);
                    }
                }
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        if (results.isEmpty()) {
            System.out.println("No guests found.");
        } else {
            System.out.println("\n-   RESULTS (" + results.size() + " guests)    -");
            for (Guest g : results) {
                System.out.println("ID: " + g.getId() + " | Name: " + g.getFirstName() + " " + g.getLastName() +
                        " | Email: " + g.getEmail() + " | Phone: " + g.getPhone() +
                        " | Nationality: " + g.getNationality());
            }
        }

        
    }

    private static void manageRooms() {
        System.out.println("\n-   MANAGE ROOMS   -");
        System.out.println("1. View all rooms");
        System.out.println("2. Add new room");
        System.out.println("3. Update room price");
        System.out.println("4. Back to main menu");

        int choice = getIntInput("Your choice: "); //to do

        switch (choice) {
            case 1:
                viewAllRooms();
                break;
            case 2:
                addNewRoom();
                break;
            case 3:
                updateRoomPrice();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void viewAllRooms() {
        System.out.println("\n-   ALL ROOMS   -");

        ISingleRepository singleRepo = SingleRepository.getRepository();
        IDoubleRepository doubleRepo = DoubleRepository.getRepository();
        ISuiteRepository suiteRepo = SuiteRepository.getRepository();

        List<Single> singles = singleRepo.getAll();
        List<DoubleRoom> doubleRooms = doubleRepo.getAll();
        List<Suite> suites = suiteRepo.getAll();

        System.out.println("\nSINGLE ROOMS:");
        for (Single s : singles) {
            System.out.println("- Room " + s.getRoomNumber() + ": €" + s.getPricePerNight() +
                    "/night, " + s.getReservations().size() + " reservation(s)");
        }

        System.out.println("\nDOUBLE ROOMS:");
        for (DoubleRoom d : doubleRooms) {
            System.out.println("- Room " + d.getRoomNumber() + ": €" + d.getPricePerNight() +
                    "/night, " + d.getReservations().size() + " reservation(s)");
        }

        System.out.println("\nSUITES:");
        for (Suite s : suites) {
            System.out.println("- Suite " + s.getRoomNumber() +
                    ": €" + s.getPricePerNight() + "/night, " +
                    s.getNbRooms() + " rooms, " +
                    "max " + s.getNbGuests() + " guests, " +
                    s.getReservations().size() + " reservation(s)");
        }
    }

    private static void addNewRoom() {
        System.out.println("\n-    ADD NEW ROOM    -");
        System.out.println("1. Single room");
        System.out.println("2. Double room");
        System.out.println("3. Suite");

        int type = getIntInput("Room type: ");

        System.out.print("Room number: ");
        int roomNumber = getIntInput("");

        System.out.print("Price per night: ");
        double price = getDoubleInput("");

        List<Reservation> emptyReservations = new ArrayList<>();

        switch (type) {
            case 1:
                System.out.print("Bed type: ");
                String bedType = scanner.nextLine();
                Single single = SingleFactory.createSingle(roomNumber, price, bedType, emptyReservations);
                if (single != null) {
                    SingleRepository.getRepository().create(single);
                    System.out.println("SUCCESS: Single room added!");
                } else {
                    System.out.println("ERROR: Failed to create room!");
                }
                break;
            case 2:
                System.out.print("Bed type: ");
                String doubleBedType = scanner.nextLine();
                DoubleRoom doubleRoom = DoubleFactory.createDouble(roomNumber, price, doubleBedType, emptyReservations);
                if (doubleRoom != null) {
                    DoubleRepository.getRepository().create(doubleRoom);
                    System.out.println("SUCCESS: Double room added!");
                } else {
                    System.out.println("ERROR: Failed to create room!");
                }
                break;
            case 3:
                System.out.print("Number of rooms: ");
                int nbRooms = getIntInput("");
                System.out.print("Max guests: ");
                int nbGuests = getIntInput("");
                Suite suite = SuiteFactory.createSuite(roomNumber, price, nbRooms, nbGuests, emptyReservations);
                if (suite != null) {
                    SuiteRepository.getRepository().create(suite);
                    System.out.println("SUCCESS: Suite added!");
                } else {
                    System.out.println("ERROR: Failed to create suite!");
                }
                break;
            default:
                System.out.println("Invalid room type!");
        }
    }

    private static void updateRoomPrice() {
        System.out.println("\n-    UPDATE ROOM PRICE    -");

        System.out.print("Enter room number: ");
        int roomNumber = getIntInput("");

        Room room = findRoomByNumber(roomNumber);
        if (room == null) {
            System.out.println("ERROR: Room not found!");
            return;
        }

        System.out.println("Current price: €" + room.getPricePerNight());
        System.out.print("New price: ");
        double newPrice = getDoubleInput("");

        System.out.println("SUCCESS: Price updated to €" + newPrice + " for room " + roomNumber);
    }

    private static void displayRoomCalendar() {
        System.out.println("\n ROOM CALENDAR ");

        List<Room> allRooms = getAllRooms();

        System.out.println("\nSelect a room to view its calendar:");
        for (int i = 0; i < allRooms.size(); i++) {
            Room room = allRooms.get(i);
            System.out.println((i + 1) + ". Room " + room.getRoomNumber() +
                    " (" + getRoomType(room) + ") - €" + room.getPricePerNight() + "/night");
        }

        System.out.print("\nSelect room number or 0 to view all: ");
        int choice = getIntInput("");

        if (choice == 0) {
            displayAllRoomsCalendar();
        } else if (choice > 0 && choice <= allRooms.size()) {
            Room selectedRoom = allRooms.get(choice - 1);
            displaySingleRoomCalendar(selectedRoom);
        } else {
            System.out.println("Invalid selection!");
        }
    }

    private static void displayAllRoomsCalendar() {
        System.out.println("\n ALL ROOMS CALENDAR ");

        // Get current month
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        System.out.print("Enter month (1-12, default " + (currentMonth + 1) + "): ");
        int month = getIntInput("");
        if (month < 1 || month > 12) month = currentMonth + 1;

        System.out.print("Enter year (default " + currentYear + "): ");
        int year = getIntInput("");
        if (year < 2024) year = currentYear;

        // Display calendar for each room
        List<Room> allRooms = getAllRooms();
        Collections.sort(allRooms, Comparator.comparingInt(Room::getRoomNumber));

        for (Room room : allRooms) {
            displayRoomMonthCalendar(room, month - 1, year);
            System.out.println();
        }
    }

    private static void displaySingleRoomCalendar(Room room) {
        System.out.println("\n CALENDAR FOR ROOM " + room.getRoomNumber());
        System.out.println("Type: " + getRoomType(room));
        System.out.println("Price: €" + room.getPricePerNight() + "/night");
        System.out.println("Reservations: " + room.getReservations().size());

        // Get current month
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        boolean viewing = true;
        while (viewing) {
            System.out.println("\n1. View current month");
            System.out.println("2. View specific month");
            System.out.println("3. View next 3 months");
            System.out.println("4. Back to menu");

            int choice = getIntInput("Your choice: ");

            switch (choice) {
                case 1:
                    displayRoomMonthCalendar(room, currentMonth, currentYear);
                    break;
                case 2:
                    System.out.print("Enter month (1-12): ");
                    int month = getIntInput("");
                    System.out.print("Enter year: ");
                    int year = getIntInput("");
                    if (month >= 1 && month <= 12) {
                        displayRoomMonthCalendar(room, month - 1, year);
                    }
                    break;
                case 3:
                    for (int i = 0; i < 3; i++) {
                        Calendar monthCal = Calendar.getInstance();
                        monthCal.add(Calendar.MONTH, i);
                        int m = monthCal.get(Calendar.MONTH);
                        int y = monthCal.get(Calendar.YEAR);
                        System.out.println("\n--- " + getMonthName(m) + " " + y + " ---");
                        displayRoomMonthCalendar(room, m, y);
                    }
                    break;
                case 4:
                    viewing = false;
                    break;
            }
        }
    }

    private static void displayRoomMonthCalendar(Room room, int month, int year) {
        System.out.println("\n--- Room " + room.getRoomNumber() + " - " + getMonthName(month) + " " + year + " ---");

        // Create calendar
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);

        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        // Calendar header
        System.out.println(" Su Mo Tu We Th Fr Sa");

        for (int i = 1; i < firstDayOfWeek; i++) {
            System.out.print("   ");
        }

        // Days of month
        for (int day = 1; day <= daysInMonth; day++) {
            cal.set(year, month, day);
            Date currentDate = cal.getTime();

            // Check if day is reserved
            boolean isReserved = isDateReserved(room, currentDate);
            boolean isCheckIn = isCheckInDate(room, currentDate);
            boolean isCheckOut = isCheckOutDate(room, currentDate);

            String dayStr = String.format("%2d", day);

            if (isCheckIn) {
                System.out.print("["+dayStr + "]"); // Check-in
            } else if (isCheckOut) {
                System.out.print("(" + dayStr + ")"); // Check-out
            } else if (isReserved) {
                System.out.print(" X "); // Occupied
            } else {
                System.out.print(" " + dayStr + " "); // Available
            }

            // New line at end of week
            if ((day + firstDayOfWeek - 1) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println("\n\nLegend: [1] = Check-in, (1) = Check-out, X = Occupied, 1 = Available");

        // Display reservations this month
        List<Reservation> monthReservations = getReservationsForMonth(room, month, year);
        if (!monthReservations.isEmpty()) {
            System.out.println("\nReservations this month:");
            for (Reservation res : monthReservations) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
                System.out.println("- " + sdf.format(res.getArrival()) + " to " +
                        sdf.format(res.getDeparture()));
            }
        }
    }

    private static void checkRoomAvailabilityForDates() {
        System.out.println("\n-   CHECK ROOM AVAILABILITY    -");

        System.out.print("Check-in date (YYYY-MM-DD): ");
        Date checkIn = promptForDate("");

        System.out.print("Check-out date (YYYY-MM-DD): ");
        Date checkOut = promptForDate("");

        if (!checkOut.after(checkIn)) {
            System.out.println("ERROR: Check-out must be after check-in!");
            return;
        }

        System.out.println("\n-   ROOMS AVAILABLE from " +
                dateFormat.format(checkIn) + " to " +
                dateFormat.format(checkOut) + "   -");

        List<Room> availableRooms = new ArrayList<>();
        List<Room> allRooms = getAllRooms();

        for (Room room : allRooms) {
            if (isRoomAvailableForPeriod(room, checkIn, checkOut)) {
                availableRooms.add(room);
            }
        }

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for selected dates.");

            suggestAlternativeDates(checkIn, checkOut);
        } else {
            displayRoomsByType("Single", filterRoomsByType(availableRooms, Single.class));
            displayRoomsByType("Double", filterRoomsByType(availableRooms, DoubleRoom.class));
            displayRoomsByType("Suite", filterRoomsByType(availableRooms, Suite.class));

            System.out.println("\nTotal: " + availableRooms.size() + " room(s) available");

            // Option to view calendar
            System.out.print("\nView calendar for a specific room? (yes/no): ");
            String response = scanner.nextLine().toLowerCase();
            if (response.equals("yes") || response.equals("y")) {
                System.out.print("Enter room number: ");
                int roomNum = getIntInput("");
                Room selectedRoom = findRoomByNumber(roomNum);
                if (selectedRoom != null && availableRooms.contains(selectedRoom)) {
                    displaySingleRoomCalendar(selectedRoom);
                }
            }
        }
    }

    private static void showAvailableRoomsForDates(Date checkIn, Date checkOut) {
        System.out.println("\n-   AVAILABLE ROOMS   -");
        System.out.println("Period: " + dateFormat.format(checkIn) + " to " + dateFormat.format(checkOut));

        ISingleRepository singleRepo = SingleRepository.getRepository();
        IDoubleRepository doubleRepo = DoubleRepository.getRepository();
        ISuiteRepository suiteRepo = SuiteRepository.getRepository();

        List<Single> singles = singleRepo.getAll();
        List<DoubleRoom> doubleRooms = doubleRepo.getAll();
        List<Suite> suites = suiteRepo.getAll();

        int availableCount = 0;

        System.out.println("\nSINGLE ROOMS:");
        for (Single s : singles) {
            if (isRoomAvailableForPeriod(s, checkIn, checkOut)) {
                System.out.println("- Room " + s.getRoomNumber() + ": €" + s.getPricePerNight() + "/night");
                availableCount++;
            }
        }

        System.out.println("\nDOUBLE ROOMS:");
        for (DoubleRoom d : doubleRooms) {
            if (isRoomAvailableForPeriod(d, checkIn, checkOut)) {
                System.out.println("- Room " + d.getRoomNumber() + ": €" + d.getPricePerNight() + "/night");
                availableCount++;
            }
        }

        System.out.println("\nSUITES:");
        for (Suite s : suites) {
            if (isRoomAvailableForPeriod(s, checkIn, checkOut)) {
                System.out.println("- Suite " + s.getRoomNumber() + ": €" + s.getPricePerNight() + "/night");
                availableCount++;
            }
        }

        if (availableCount == 0) {
            System.out.println("\nNo rooms available for these dates.");
        } else {
            System.out.println("\nTotal: " + availableCount + " room(s) available");
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private static Date promptForDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String dateStr = scanner.nextLine();
                return dateFormat.parse(dateStr);
            } catch (Exception e) {
                System.out.println("Invalid date format! Use YYYY-MM-DD");
            }
        }
    }

    private static Room findRoomByNumber(int roomNumber) {
        ISingleRepository singleRepo = SingleRepository.getRepository();
        IDoubleRepository doubleRepo = DoubleRepository.getRepository();
        ISuiteRepository suiteRepo = SuiteRepository.getRepository();

        Single single = singleRepo.read(roomNumber);
        if (single != null) return single;

        DoubleRoom doubleRoom = doubleRepo.read(roomNumber);
        if (doubleRoom != null) return doubleRoom;

        Suite suite = suiteRepo.read(roomNumber);
        return suite;
    }

    private static boolean isRoomAvailable(Room room, Date start, Date end) {
        for (Reservation res : room.getReservations()) {
            if (datesOverlap(start, end, res.getArrival(), res.getDeparture())) {
                return false;
            }
        }
        return true;
    }

    private static boolean isRoomAvailableForPeriod(Room room, Date start, Date end) {
        return isRoomAvailable(room, start, end);
    }

    private static boolean datesOverlap(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
    }

    private static void addReservationToRoom(Room room, Reservation reservation) {
        room.getReservations().add(reservation);
    }

    private static void removeReservationFromRoom(Room room, Reservation reservation) {
        room.getReservations().remove(reservation);
    }

    private static Room findRoomByReservation(Reservation reservation) {
        ISingleRepository singleRepo = SingleRepository.getRepository();
        IDoubleRepository doubleRepo = DoubleRepository.getRepository();
        ISuiteRepository suiteRepo = SuiteRepository.getRepository();

        List<Single> singles = singleRepo.getAll();
        List<DoubleRoom> doubleRooms = doubleRepo.getAll();
        List<Suite> suites = suiteRepo.getAll();

        for (Single s : singles) {
            if (s.getReservations().contains(reservation)) return s;
        }
        for (DoubleRoom d : doubleRooms) {
            if (d.getReservations().contains(reservation)) return d;
        }
        for (Suite s : suites) {
            if (s.getReservations().contains(reservation)) return s;
        }
        return null;
    }
}//to delete