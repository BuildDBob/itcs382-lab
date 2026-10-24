package bikeshare;

/** The three campus stations. */
enum Station {
    LIBRARY, CANTEEN, DORM;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
