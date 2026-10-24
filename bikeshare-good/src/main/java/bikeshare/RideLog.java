package bikeshare;

/** Somewhere finished rides are kept. */
interface RideLog {
    void save(Ride ride);
}
