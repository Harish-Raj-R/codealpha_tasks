public class Booking {
    private String name;
    private int roomNumber;
    private String category;

    public Booking(String name, int roomNumber, String category) {
        this.name = name;
        this.roomNumber = roomNumber;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return name + "," + roomNumber + "," + category;
    }

    public static Booking fromString(String line) {
        String[] parts = line.split(",");
        return new Booking(parts[0], Integer.parseInt(parts[1]), parts[2]);
    }
}
