public class Room {
    private int roomNumber;
    private String category;
    private boolean isAvailable;

    public Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean status) {
        this.isAvailable = status;
    }

    @Override
    public String toString() {
        return roomNumber + "," + category + "," + isAvailable;
    }

    public static Room fromString(String line) {
        String[] parts = line.split(",");
        Room room = new Room(Integer.parseInt(parts[0]), parts[1]);
        room.setAvailability(Boolean.parseBoolean(parts[2]));
        return room;
    }
}
