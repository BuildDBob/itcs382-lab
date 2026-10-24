package bikeshare;

import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/** The keyboard interface: turns typed commands into calls on BikeShare and prints the results. */
final class Menu {
    private static final String HELP = """
            Commands:
              rent <memberId> <student|staff> <library|canteen|dorm>
              return <memberId> <library|canteen|dorm> <minutes>
              quit""";

    private final BikeShare bikeShare;
    private final Scanner in;
    private final PrintStream out;

    Menu(BikeShare bikeShare, Scanner in, PrintStream out) {
        this.bikeShare = bikeShare;
        this.in = in;
        this.out = out;
    }

    void run() {
        out.println(HELP);
        while (true) {
            out.print("> ");
            if (!in.hasNextLine()) {
                return;
            }
            String[] words = in.nextLine().trim().split("\\s+");
            if (words[0].equalsIgnoreCase("quit")) {
                return;
            }
            try {
                handle(words);
            } catch (IllegalArgumentException | IllegalStateException | UncheckedIOException e) {
                out.println("Error: " + e.getMessage());
            }
        }
    }

    private void handle(String[] words) {
        switch (words[0].toLowerCase(Locale.ROOT)) {
            case "rent" -> rent(words);
            case "return" -> giveBack(words);
            case "" -> { }
            default -> out.println(HELP);
        }
    }

    private void rent(String[] words) {
        requireWords(words, 4);
        Bike bike = bikeShare.rent(words[1], parse(MemberType.class, words[2]), parse(Station.class, words[3]));
        out.println("Rented bike " + bike + " to " + words[1]);
    }

    private void giveBack(String[] words) {
        requireWords(words, 4);
        Ride ride = bikeShare.giveBack(words[1], parse(Station.class, words[2]), parseMinutes(words[3]));
        printReceipt(ride);
    }

    private void printReceipt(Ride ride) {
        out.println("---- Receipt ----");
        out.println("Bike:    " + ride.bike());
        out.println("Minutes: " + ride.minutes());
        out.println("Fee:     " + ride.fee() + " baht");
        out.println("-----------------");
    }

    private static void requireWords(String[] words, int count) {
        if (words.length != count) {
            throw new IllegalArgumentException("Wrong number of words. Type help for the commands.");
        }
    }

    private static <E extends Enum<E>> E parse(Class<E> type, String text) {
        try {
            return Enum.valueOf(type, text.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown " + type.getSimpleName() + " '" + text
                    + "'. Choose from " + Arrays.toString(type.getEnumConstants()));
        }
    }

    private static int parseMinutes(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Minutes must be a whole number, not '" + text + "'");
        }
    }
}
