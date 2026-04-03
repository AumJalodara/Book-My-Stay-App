import java.util.*;

public class BookMyStay {

    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    static class RoomInventory {
        private Set<String> availableRoomTypes;

        public RoomInventory() {
            availableRoomTypes = new HashSet<>();
            availableRoomTypes.add("Single");
            availableRoomTypes.add("Double");
            availableRoomTypes.add("Suite");
        }

        public boolean isValidRoomType(String roomType) {
            return availableRoomTypes.contains(roomType);
        }
    }

    static class BookingRequestQueue {
        public void addRequest(String guestName, String roomType) {
            System.out.println("Booking successful for " + guestName + " (" + roomType + ")");
        }
    }

    static class ReservationValidator {
        public void validate(String guestName, String roomType, RoomInventory inventory)
                throws InvalidBookingException {

            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }

            if (!inventory.isValidRoomType(roomType)) {
                throw new InvalidBookingException("Invalid room type selected.");
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            validator.validate(name, roomType, inventory);

            bookingQueue.addRequest(name, roomType);

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}