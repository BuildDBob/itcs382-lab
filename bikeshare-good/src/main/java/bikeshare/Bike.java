package bikeshare;

/** A bike, known by its ID. */
record Bike(String id) {
    @Override
    public String toString() {
        return id;
    }
}
