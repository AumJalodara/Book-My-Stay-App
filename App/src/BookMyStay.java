import java.util.*;

public class BookMyStay {

    // Reservation
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // Inventory
    static class RoomInventory {
        private Map<String, Integer> availability;

        public RoomInventory() {
            availability = new HashMap<>();
            availability.put("Single", 2);
            availability.put("Double", 1);
            availability.put("Suite", 1);
        }

        public int getAvailable(String type) {
            return availability.getOrDefault(type, 0);
        }

        public void reduceRoom(String type) {
            availability.put(type, availability.get(type) - 1);
        }
    }

    // Booking Queue (FIFO)
    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public void add(Reservation r) {
            queue.offer(r);
        }

        public Reservation next() {
            return queue.poll();
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }
    }

    // Room Allocation Service
    static class RoomAllocationService {

        private Set<String> allocatedRoomIds = new HashSet<>();
        private Map<String, Integer> counters = new HashMap<>();

        public void allocateRoom(Reservation r, RoomInventory inventory) {
            String type = r.getRoomType();

            if (inventory.getAvailable(type) > 0) {
                String roomId = generateRoomId(type);
                allocatedRoomIds.add(roomId);
                inventory.reduceRoom(type);

                System.out.println("Booking confirmed for Guest: "
                        + r.getGuestName()
                        + ", Room ID: "
                        + roomId);
            } else {
                System.out.println("No rooms available for " + r.getGuestName());
            }
        }

        private String generateRoomId(String type) {
            int count = counters.getOrDefault(type, 0) + 1;
            counters.put(type, count);
            return type + "-" + count;
        }
    }

    // MAIN
    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService service = new RoomAllocationService();

        // Add bookings
        queue.add(new Reservation("Abhi", "Single"));
        queue.add(new Reservation("Subha", "Single"));
        queue.add(new Reservation("Vanmathi", "Suite"));

        // Process FIFO
        while (queue.hasNext()) {
            Reservation r = queue.next();
            service.allocateRoom(r, inventory);
        }
    }
}