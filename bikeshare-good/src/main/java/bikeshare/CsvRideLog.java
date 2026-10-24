package bikeshare;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/** Appends each ride as one line of a CSV file, writing a header when the file is new. */
final class CsvRideLog implements RideLog {
    private static final String HEADER = "member,type,bike,from,to,minutes,fee";

    private final Path file;

    CsvRideLog(Path file) {
        this.file = file;
    }

    @Override
    public void save(Ride ride) {
        String lines = (Files.exists(file) ? "" : HEADER + System.lineSeparator())
                + toCsv(ride) + System.lineSeparator();
        try {
            Files.writeString(file, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save the ride to " + file, e);
        }
    }

    private static String toCsv(Ride ride) {
        return String.join(",", quote(ride.memberId()), ride.memberType().name(), ride.bike().id(),
                ride.from().name(), ride.to().name(),
                String.valueOf(ride.minutes()), String.valueOf(ride.fee()));
    }

    private static String quote(String field) {
        boolean needsQuotes = field.contains(",") || field.contains("\"");
        return needsQuotes ? '"' + field.replace("\"", "\"\"") + '"' : field;
    }
}
