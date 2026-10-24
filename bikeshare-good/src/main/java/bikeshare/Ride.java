package bikeshare;

/** A finished ride. The fee is derived, so it can never disagree with the minutes. */
record Ride(String memberId, MemberType memberType, Bike bike, Station from, Station to, int minutes) {
    int fee() {
        return memberType.feeFor(minutes);
    }
}
