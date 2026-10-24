package bikeshare;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/** The lending rules: which bikes are where, and who is riding what. */
final class BikeShare {
    private static final int BIKES_PER_STATION = 2;

    private final Map<Station, Deque<Bike>> docks = new EnumMap<>(Station.class);
    private final Map<String, Rental> rentals = new HashMap<>();
    private final RideLog rideLog;

    BikeShare(RideLog rideLog) {
        this.rideLog = rideLog;
        int nextId = 1;
        for (Station station : Station.values()) {
            Deque<Bike> bikes = new ArrayDeque<>();
            for (int i = 0; i < BIKES_PER_STATION; i++) {
                bikes.offer(new Bike("B" + nextId++));
            }
            docks.put(station, bikes);
        }
    }

    Bike rent(String memberId, MemberType memberType, Station station) {
        if (rentals.containsKey(memberId)) {
            throw new IllegalStateException(memberId + " already has a bike");
        }
        Bike bike = docks.get(station).poll();
        if (bike == null) {
            throw new IllegalStateException("No bike available at " + station);
        }
        rentals.put(memberId, new Rental(memberType, bike, station));
        return bike;
    }

    Ride giveBack(String memberId, Station station, int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("Minutes must be positive");
        }
        Rental rental = rentals.get(memberId);
        if (rental == null) {
            throw new IllegalStateException(memberId + " has no bike to return");
        }
        Ride ride = new Ride(memberId, rental.memberType(), rental.bike(), rental.from(), station, minutes);
        rideLog.save(ride); // save first: if saving fails, the rental stays open and can be retried
        rentals.remove(memberId);
        docks.get(station).offer(ride.bike());
        return ride;
    }
}
