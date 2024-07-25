import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OnlineReservationSystem {
    public static void main(String[] args) {
        ReservationSystem reservationSystem = new ReservationSystem();
        reservationSystem.start();
    }
}

class ReservationSystem {
    private List<User> users;
    private List<Reservation> reservations;
    private Scanner scanner;
    private User currentUser;

    public ReservationSystem() {
        users = new ArrayList<>();
        reservations = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeUsers();
    }

    private void initializeUsers() {
        // Add some users
        users.add(new User("user1", "password1"));
        users.add(new User("user2", "password2"));
    }

    public void start() {
        System.out.println("Welcome to the Online Reservation System");
        if (authenticateUser()) {
            showMenu();
        } else {
            System.out.println("Invalid login credentials.");
        }
    }

    private boolean authenticateUser() {
        System.out.print("Enter login ID: ");
        String loginId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getLoginId().equals(loginId) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    private void showMenu() {
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Make a Reservation");
            System.out.println("2. Cancel a Reservation");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    makeReservation();
                    break;
                case 2:
                    cancelReservation();
                    break;
                case 3:
                    System.out.println("Logging out. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    private void makeReservation() {
        System.out.println("Making a new reservation...");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter train number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Enter train name: ");
        String trainName = scanner.nextLine();
        System.out.print("Enter class type: ");
        String classType = scanner.nextLine();
        System.out.print("Enter date of journey (YYYY-MM-DD): ");
        String dateOfJourney = scanner.nextLine();
        System.out.print("Enter from (place): ");
        String fromPlace = scanner.nextLine();
        System.out.print("Enter to (destination): ");
        String toDestination = scanner.nextLine();

        Reservation reservation = new Reservation(name, trainNumber, trainName, classType, dateOfJourney, fromPlace, toDestination);
        reservations.add(reservation);
        System.out.println("Reservation successful. Your PNR number is " + reservation.getPnrNumber());
    }

    private void cancelReservation() {
        System.out.print("Enter PNR number to cancel: ");
        String pnrNumber = scanner.nextLine();

        Reservation reservationToCancel = null;
        for (Reservation reservation : reservations) {
            if (reservation.getPnrNumber().equals(pnrNumber)) {
                reservationToCancel = reservation;
                break;
            }
        }

        if (reservationToCancel != null) {
            System.out.println("Reservation found: " + reservationToCancel);
            System.out.print("Do you want to cancel this reservation? (yes/no): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("yes")) {
                reservations.remove(reservationToCancel);
                System.out.println("Reservation cancelled successfully.");
            } else {
                System.out.println("Cancellation aborted.");
            }
        } else {
            System.out.println("Reservation not found with PNR number: " + pnrNumber);
        }
    }
}

class User {
    private String loginId;
    private String password;

    public User(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
}

class Reservation {
    private static int counter = 1000;
    private String pnrNumber;
    private String name;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String dateOfJourney;
    private String fromPlace;
    private String toDestination;

    public Reservation(String name, String trainNumber, String trainName, String classType, String dateOfJourney, String fromPlace, String toDestination) {
        this.pnrNumber = "PNR" + (++counter);
        this.name = name;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.fromPlace = fromPlace;
        this.toDestination = toDestination;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "pnrNumber='" + pnrNumber + '\'' +
                ", name='" + name + '\'' +
                ", trainNumber='" + trainNumber + '\'' +
                ", trainName='" + trainName + '\'' +
                ", classType='" + classType + '\'' +
                ", dateOfJourney='" + dateOfJourney + '\'' +
                ", fromPlace='" + fromPlace + '\'' +
                ", toDestination='" + toDestination + '\'' +
                '}';
    }
}
