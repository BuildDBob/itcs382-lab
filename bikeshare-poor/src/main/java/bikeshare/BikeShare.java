package bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BikeShare {
    public static final int STUDENT = 1;
    public static final int STAFF = 2;
    public static String[] stations = { "Library", "Canteen", "Dorm" };
    public static String[] bikes = { "B1", "B2", "B3", "B4", "B5", "B6" };
    public static String[] where = { "Library", "Library", "Canteen", "Canteen", "Dorm", "Dorm" };
    public static String[] rider = new String[6];
    public static int[] riderType = new int[6];
    public static Scanner in;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            in = scanner;
            String cmd = ask("Command (rent, return, quit): ");
            while (!cmd.equalsIgnoreCase("quit")) {
                if (cmd.equalsIgnoreCase("rent")) {
                    String id = ask("Member ID: ");
                    String type = ask("Member type (student or staff): ");
                    String station = ask("Station (Library, Canteen, Dorm): ");
                    int code = type.equalsIgnoreCase("student") ? STUDENT : type.equalsIgnoreCase("staff") ? STAFF : 0;
                    if (id.isEmpty() || id.contains(",")) {
                        System.out.println("Invalid member ID.");
                    } else if (code == 0) {
                        System.out.println("Unknown member type.");
                    } else {
                        System.out.println(handle(id, code, station, 0, false));
                    }
                } else if (cmd.equalsIgnoreCase("return")) {
                    String id = ask("Member ID: ");
                    String station = ask("Station (Library, Canteen, Dorm): ");
                    try {
                        int minutes = Integer.parseInt(ask("Ride time in minutes: "));
                        System.out.println(
                                minutes > 0 ? handle(id, 0, station, minutes, true) : "Minutes must be positive.");
                    } catch (NumberFormatException e) {
                        System.out.println("Minutes must be a whole number.");
                    }
                } else {
                    System.out.println("Unknown command.");
                }
                cmd = ask("Command (rent, return, quit): ");
            }
        }
    }

    public static String ask(String prompt) {
        System.out.print(prompt);
        return in.hasNextLine() ? in.nextLine().trim() : "quit";
    }

    public static String handle(String id, int type, String station, int minutes, boolean returning) {
        String name = null;
        for (String s : stations) {
            if (s.equalsIgnoreCase(station)) {
                name = s;
            }
        }
        if (name == null) {
            return "Unknown station.";
        }
        int held = -1;
        for (int i = 0; i < bikes.length; i++) {
            if (id.equals(rider[i])) {
                held = i;
            }
        }
        if (returning) {
            if (held < 0) {
                return "Member " + id + " has no bike.";
            }
            int fee = minutes * (riderType[held] == STUDENT ? 2 : 3);
            String line = bikes[held] + "," + id + "," + riderType[held] + "," + minutes + "," + fee;
            String receipt = "--- Receipt ---\nBike: " + bikes[held] + "\nMinutes: " + minutes + "\nFee: " + fee
                    + " baht";
            where[held] = name;
            rider[held] = null;
            riderType[held] = 0;
            try (FileWriter out = new FileWriter("rides.csv", true)) {
                out.write(line + System.lineSeparator());
            } catch (IOException e) {
                receipt += "\nWarning: ride not saved (" + e.getMessage() + ")";
            }
            return receipt;
        }
        if (held >= 0) {
            return "Member " + id + " already has bike " + bikes[held] + ".";
        }
        for (int i = 0; i < bikes.length; i++) {
            if (name.equals(where[i])) {
                where[i] = null;
                rider[i] = id;
                riderType[i] = type;
                return "Rented bike " + bikes[i] + " from " + name + ".";
            }
        }
        return "No bike available at " + name + ".";
    }
}
