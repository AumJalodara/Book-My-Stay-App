import java.util.*;
import java.io.*;

public class BookMyStay {

    static class RoomInventory {
        private Map<String, Integer> rooms = new HashMap<>();

        public RoomInventory() {
            rooms.put("Single", 5);
            rooms.put("Double", 3);
            rooms.put("Suite", 2);
        }

        public Map<String, Integer> getRooms() {
            return rooms;
        }

        public void setRoom(String type, int count) {
            rooms.put(type, count);
        }

        public void printInventory() {
            System.out.println("\nCurrent Inventory:");
            for (String key : rooms.keySet()) {
                System.out.println(key + ": " + rooms.get(key));
            }
        }
    }

    static class FilePersistenceService {

        public void saveInventory(RoomInventory inventory, String filePath) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (Map.Entry<String, Integer> entry : inventory.getRooms().entrySet()) {
                    writer.write(entry.getKey() + "=" + entry.getValue());
                    writer.newLine();
                }
                System.out.println("Inventory saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving inventory.");
            }
        }

        public void loadInventory(RoomInventory inventory, String filePath) {
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    inventory.setRoom(parts[0], Integer.parseInt(parts[1]));
                }
            } catch (IOException e) {
                System.out.println("Error loading inventory.");
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService service = new FilePersistenceService();

        service.loadInventory(inventory, filePath);

        inventory.printInventory();

        service.saveInventory(inventory, filePath);
    }
}