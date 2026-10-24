package bikeshare;

/** The kinds of member and the per-minute rate each one pays. */
enum MemberType {
    STUDENT(2),
    STAFF(3);

    private final int bahtPerMinute;

    MemberType(int bahtPerMinute) {
        this.bahtPerMinute = bahtPerMinute;
    }

    int feeFor(int minutes) {
        return bahtPerMinute * minutes;
    }
}
