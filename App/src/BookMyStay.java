import java.util.*;

public class BookMyStay {

    static class Reservation {
        String guestName;
        String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public void addRequest(Reservation r) {
            queue.add(r);
        }

        public Reservation getRequest() {
            return queue.poll();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    static class RoomInventory {
        private Map<String, Integer> rooms = new HashMap<>();

        public RoomInventory() {
            rooms.put("Single", 5);
            rooms.put("Double", 3);
            rooms.put("Suite", 2);
        }

        public boolean allocate(String type) {
            if (rooms.get(type) > 0) {
                rooms.put(type, rooms.get(type) - 1);
                return true;
            }
            return false;
        }

        public void printInventory() {
            System.out.println("\nRemaining Inventory:");
            for (String key : rooms.keySet()) {
                System.out.println(key + ": " + rooms.get(key));
            }
        }
    }

    static class RoomAllocationService {
        private Map<String, Integer> counters = new HashMap<>();

        public RoomAllocationService() {
            counters.put("Single", 1);
            counters.put("Double", 1);
            counters.put("Suite", 1);
        }

        public void allocateRoom(Reservation r, RoomInventory inventory) {
            if (inventory.allocate(r.roomType)) {
                int id = counters.get(r.roomType);
                System.out.println("Booking confirmed for Guest: " + r.guestName +
                        ", Room ID: " + r.roomType + "-" + id);
                counters.put(r.roomType, id + 1);
            }
        }
    }

    static class ConcurrentBookingProcessor implements Runnable {
        private BookingRequestQueue bookingQueue;
        private RoomInventory inventory;
        private RoomAllocationService allocationService;

        public ConcurrentBookingProcessor(
                BookingRequestQueue bookingQueue,
                RoomInventory inventory,
                RoomAllocationService allocationService) {
            this.bookingQueue = bookingQueue;
            this.inventory = inventory;
            this.allocationService = allocationService;
        }

        @Override
        public void run() {
            while (true) {
                Reservation reservation;

                synchronized (bookingQueue) {
                    if (bookingQueue.isEmpty()) break;
                    reservation = bookingQueue.getRequest();
                }

                synchronized (inventory) {
                    allocationService.allocateRoom(reservation, inventory);
                }
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Double"));
        queue.addRequest(new Reservation("Kural", "Suite"));
        queue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        inventory.printInventory();
    }
}