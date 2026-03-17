public class BookMyStay {

    // Abstract Room class
    static abstract class Room {
        protected int numberOfBeds;
        protected int squareFeet;
        protected double pricePerNight;

        public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
            this.numberOfBeds = numberOfBeds;
            this.squareFeet = squareFeet;
            this.pricePerNight = pricePerNight;
        }

        public void displayRoomDetails(int available) {
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Size: " + squareFeet + " sqft");
            System.out.println("Price per night: " + pricePerNight);
            System.out.println("Available: " + available);
        }
    }

    // Single Room
    static class SingleRoom extends Room {
        public SingleRoom() {
            super(1, 250, 1500.0);
        }
    }

    // Double Room
    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super(2, 400, 2500.0);
        }
    }

    // Suite Room
    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super(3, 750, 5000.0);
        }
    }

    // Main method
    public static void main(String[] args) {

        SingleRoom single = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("Hotel Room Initialization\n");

        System.out.println("Single Room:");
        single.displayRoomDetails(singleAvailable);

        System.out.println("\nDouble Room:");
        doubleRoom.displayRoomDetails(doubleAvailable);

        System.out.println("\nSuite Room:");
        suite.displayRoomDetails(suiteAvailable);
    }
}