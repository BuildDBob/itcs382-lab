package bikeshare;

/** A ride that has started but not yet ended. */
record Rental(MemberType memberType, Bike bike, Station from) {
}
