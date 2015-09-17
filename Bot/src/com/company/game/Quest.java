package com.company.game;

public class Quest {
    public enum Status {
        FAILED, SUCCEDED, AVAILAVBLE
    };

    private int numberOfKnights;

    private int numberOfFails;

    private Status status;

    public int getNumberOfKnights() {
        return numberOfKnights;
    }

    public int getNumberOfFails() {
        return numberOfFails;
    }

    public Status getStatus() {
        return status;
    }

    public Quest(int knights, int fails) {
        numberOfFails = fails;
        numberOfKnights = knights;
        status = Status.AVAILAVBLE;
    }

    public void setFail() {
        if (status == Status.AVAILAVBLE) {
            status = Status.FAILED;
        }
    }

    public void setSucceded() {
        if (status == Status.AVAILAVBLE) {
            status = Status.SUCCEDED;
        }
    }

    public String toString() {
        if (numberOfFails < 2) {
            return numberOfKnights + " \n" + status;
        } else {
            return numberOfKnights + " \n" + status + "\n" + numberOfFails;
        }
    }
}
