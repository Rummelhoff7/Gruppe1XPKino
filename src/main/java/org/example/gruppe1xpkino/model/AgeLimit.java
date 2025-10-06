package org.example.gruppe1xpkino.model;

public enum AgeLimit {
    //Dansk format
    ALL(0),
    AGE_7(7),
    AGE_11(11),
    AGE_15(15);

    private final int minAge;

    AgeLimit(int minAge) {
        this.minAge = minAge;
    }

    public int getMinAge() {
        return minAge;
    }
}
