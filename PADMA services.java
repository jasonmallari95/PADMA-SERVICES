import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// ===== MAIN CLASS =====
public class SpaBookingSystem {

    static Scanner input = new Scanner(System.in);

    static String ADMIN_USER = "admin";
    static String ADMIN_PASS = "1234";

    static ArrayList<CustomerBooking> bookingList = new ArrayList<>();
    static int idCounter = 1;

    public static void main(String[] args) {
        while (true) {
            loginSystem();
        }
    }

    // ===== LOGIN SYSTEM =====
    static void loginSystem() {
        System.out.println("\n=== ADMIN LOGIN ===");

        System.out.print("Username: ");
        String user = input.nextLine();

        System.out.print("Password: ");
        String pass = input.nextLine();

        if (user.equals(ADMIN_USER) && pass.equals(ADMIN_PASS)) {
            System.out.println("Login Successful!");
            mainMenu();
        } else {
            System.out.println("Invalid Login!");
        }
    }

    // ===== MAIN MENU =====
    static void mainMenu() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Add Booking");
            System.out.println("2. Manage Bookings");
            System.out.println("3. Logout");
            System.out.println("4. Exit");
            System.out.print("Choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    addBooking();
                    break;
                case "2":
                    manageBookings();
                    break;
                case "3":
                    return;
                case "4":
                    System.out.println("Exiting system...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ===== ADD BOOKING =====
    static void addBooking() {
        System.out.println("\n=== ADD BOOKING ===");

        System.out.print("Enter Name: ");
        String name = input.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = input.nextLine();

        System.out.println("\nSelect Service:");
        System.out.println("1. Padma Signature Massage - PHP 480.00");
        System.out.println("2. Back Massage - PHP 530.00");
        System.out.println("3. Foot Massage - PHP 450.00");
        System.out.println("4. Foot Scrub (45 mins) - PHP 400.00");
        System.out.println("5. Ventosa - PHP 620.00");
        System.out.println("6. Hot Stone Massage - PHP 620.00");
        System.out.println("7. Aromatherapy Massage - PHP 650.00");
        System.out.println("8. Foot Revival Therapy - PHP 650.00");
        System.out.println("9. Signature & Foot Massage - PHP 890.00");
        System.out.println("10. Back & Foot Massage - PHP 940.00");
        System.out.println("11. Premium Back Massage - PHP 880.00");
        System.out.println("12. Stress Relief Package - PHP 1090.00");

        // ✅ FIXED: Loop until valid service
        String service = "";
        double price = 0;
        String choice;

        while (true) {
            System.out.print("Choice: ");
            choice = input.nextLine();

            switch (choice) {
                case "1":
                    service = "Padma Signature Massage";
                    price = 480.00;
                    break;
                case "2":
                    service = "Back Massage";
                    price = 530.00;
                    break;
                case "3":
                    service = "Foot Massage";
                    price = 450.00;
                    break;
                case "4":
                    service = "Foot Scrub (45 mins)";
                    price = 400.00;
                    break;
                case "5":
                    service = "Ventosa";
                    price = 620.00;
                    break;
                case "6":
                    service = "Hot Stone Massage";
                    price = 620.00;
                    break;
                case "7":
                    service = "Aromatherapy Massage";
                    price = 650.00;
                    break;
                case "8":
                    service = "Foot Revival Therapy";
                    price = 650.00;
                    break;
                case "9":
                    service = "Signature & Foot Massage";
                    price = 890.00;
                    break;
                case "10":
                    service = "Back & Foot Massage";
                    price = 940.00;
                    break;
                case "11":
                    service = "Premium Back Massage";
                    price = 880.00;
                    break;
                case "12":
                    service = "Stress Relief Package";
                    price = 1090.00;
                    break;
                default:
                    System.out.println("Invalid service! Please choose 1 - 12.");
                    continue;
            }
            break;
        }

        String date = getValidFutureDate();
        String time = getValidTime();

        bookingList.add(new CustomerBooking(idCounter++, name, phone, service, date, time, price));

        System.out.println("Booking Added (PENDING).");
    }

    // ===== FUTURE DATE VALIDATION =====
    static String getValidFutureDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");

        while (true) {
            System.out.print("Enter Date (DD/MM/YYYY): ");
            String inputDate = input.nextLine();

            try {
                LocalDate enteredDate = LocalDate.parse(inputDate, formatter);

                String formatted = enteredDate.format(formatter);
                if (!formatted.equals(inputDate)) {
                    System.out.println("Invalid format! Use DD/MM/YYYY.");
                    continue;
                }

                LocalDate today = LocalDate.now();

                if (enteredDate.isBefore(today)) {
                    System.out.println("Invalid date! You cannot book a past date.");
                    continue;
                }

                return inputDate;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date! Please enter a valid calendar date.");
            }
        }
    }

    // ===== TIME VALIDATION =====
    static String getValidTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        while (true) {
            System.out.print("Enter Time (24-hour HH:mm): ");
            String inputTime = input.nextLine();

            try {
                LocalTime.parse(inputTime, formatter);
                return inputTime;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time! Try again.");
            }
        }
    }

    // ===== MANAGE BOOKINGS =====
    static void manageBookings() {

        if (bookingList.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        boolean hasPending = false;

        for (CustomerBooking b : bookingList) {
            if (b.status.equals("PENDING")) {
                hasPending = true;
                break;
            }
        }

        if (!hasPending) {
            System.out.println("No pending bookings.");
            return;
        }

        System.out.println("\n=== PENDING BOOKINGS ===");

        for (CustomerBooking b : bookingList) {
            if (b.status.equals("PENDING")) {
                System.out.println("ID: " + b.id + " | " + b.name);
            }
        }

        System.out.print("Enter Booking ID (0 to cancel): ");

        int id;
        try {
            id = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input!");
            return;
        }

        if (id == 0) return;

        CustomerBooking selected = null;

        for (CustomerBooking b : bookingList) {
            if (b.id == id && b.status.equals("PENDING")) {
                selected = b;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Booking not found!");
            return;
        }

        selected.showDetails();

        System.out.print("Approve (A) / Decline (D): ");
        String action = input.nextLine().toUpperCase();

        if (action.equals("A")) {
            selected.status = "APPROVED";
        } else if (action.equals("D")) {
            selected.status = "DECLINED";
        } else {
            System.out.println("Invalid option!");
            return;
        }

        System.out.println("Booking updated successfully!");
    }
}

// ===== BOOKING CLASS =====
class CustomerBooking {
    int id;
    String name;
    String phone;
    String service;
    String date;
    String time;
    String status;
    double price;

    CustomerBooking(int id, String name, String phone, String service, String date, String time, double price) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.service = service;
        this.date = date;
        this.time = time;
        this.price = price;
        this.status = "PENDING";
    }

    void showDetails() {
        System.out.println("\n--- BOOKING DETAILS ---");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Service: " + service);
        System.out.println("Price: PHP " + price);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Status: " + status);
    }
}