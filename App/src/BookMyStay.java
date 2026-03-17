import java.util.HashMap;
import java.util.Map;

public class BookMyStay {

    // Abstract Room
    static abstract class Room {
        protected int numberOfBeds;
        protected int squareFeet;
        protected double pricePerNight;

        public Room(int beds, int size, double price) {
            this.numberOfBeds = beds;
            this.squareFeet = size;
            this.pricePerNight = price;
        }

        public void display(int available) {
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Size: " + squareFeet + " sqft");
            System.out.println("Price per night: " + pricePerNight);
            System.out.println("Available: " + available);
        }
    }

    // Room Types
    static class SingleRoom extends Room {
        public SingleRoom() {
            super(1, 250, 1500.0);
        }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super(2, 400, 2500.0);
        }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super(3, 750, 5000.0);
        }
    }

    // Inventory (Read-only use)
    static class RoomInventory {
        private Map<String, Integer> roomAvailability;

        public RoomInventory() {
            roomAvailability = new HashMap<>();
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        public Map<String, Integer> getRoomAvailability() {
            return roomAvailability;
        }
    }

    // Search Service
    static class RoomSearchService {

        public void searchAvailableRooms(
                RoomInventory inventory,
                Room singleRoom,
                Room doubleRoom,
                Room suiteRoom) {

            Map<String, Integer> availability = inventory.getRoomAvailability();

            System.out.println("Room Search\n");

            // Single Room
            if (availability.get("Single") > 0) {
                System.out.println("Single Room:");
                singleRoom.display(availability.get("Single"));
                System.out.println();
            }

            // Double Room
            if (availability.get("Double") > 0) {
                System.out.println("Double Room:");
                doubleRoom.display(availability.get("Double"));
                System.out.println();
            }

            // Suite Room
            if (availability.get("Suite") > 0) {
                System.out.println("Suite Room:");
                suiteRoom.display(availability.get("Suite"));
            }
        }
    }

    // Main
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        RoomSearchService service = new RoomSearchService();

        service.searchAvailableRooms(inventory, single, doubleRoom, suite);
    }
}