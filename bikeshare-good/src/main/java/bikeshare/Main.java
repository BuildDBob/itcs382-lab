package bikeshare;

import java.nio.file.Path;
import java.util.Scanner;

/** Wires the parts together and starts the menu. */
public final class Main {
    public static void main(String[] args) {
        BikeShare bikeShare = new BikeShare(new CsvRideLog(Path.of("rides.csv")));
        new Menu(bikeShare, new Scanner(System.in), System.out).run();
    }
}
