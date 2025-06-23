import java.io.*;
import java.util.*;

public class Hotel {
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private final String roomsFile = "database/rooms.txt";
    private final String bookingsFile = "database/bookings.txt";

    public Hotel() {
        loadRooms();
        loadBookings();
    }

    public void loadRooms() {
        try (BufferedReader br = new BufferedReader(new FileReader(roomsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                rooms.add(Room.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading rooms: " + e.getMessage());
        }
    }

    public void loadBookings() {
        try (BufferedReader br = new BufferedReader(new FileReader(bookingsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                bookings.add(Booking.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
    }

    public void saveRooms() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(roomsFile))) {
            for (Room room : rooms) {
                bw.write(room.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving rooms.");
        }
    }

    public void saveBookings() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(bookingsFile))) {
            for (Booking booking : bookings) {
                bw.write(booking.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }

    public void viewAvailableRooms(String category) {
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category) && room.isAvailable()) {
                System.out.println("Room " + room.getRoomNumber() + " (" + category + ")");
            }
        }
    }

    public boolean bookRoom(String name, String category) {
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category) && room.isAvailable()) {
                room.setAvailability(false);
                bookings.add(new Booking(name, room.getRoomNumber(), category));
                saveRooms();
                saveBookings();
                System.out.println("Booking successful! Room No: " + room.getRoomNumber());
                simulatePayment();
                return true;
            }
        }
        System.out.println("No available rooms in this category.");
        return false;
    }

    public boolean cancelBooking(String name) {
        Iterator<Booking> iterator = bookings.iterator();
        while (iterator.hasNext()) {
            Booking b = iterator.next();
            if (b.getName().equalsIgnoreCase(name)) {
                for (Room r : rooms) {
                    if (r.getRoomNumber() == b.getRoomNumber()) {
                        r.setAvailability(true);
                        break;
                    }
                }
                iterator.remove();
                saveRooms();
                saveBookings();
                System.out.println("Booking cancelled successfully.");
                return true;
            }
        }
        System.out.println("No booking found for " + name);
        return false;
    }

    public void viewBookings() {
        for (Booking b : bookings) {
            System.out.println("Name: " + b.getName() + ", Room: " + b.getRoomNumber() + ", Category: " + b.getCategory());
        }
    }

    private void simulatePayment() {
        System.out.println("Processing payment...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        System.out.println("Payment successful!");
    }
}
