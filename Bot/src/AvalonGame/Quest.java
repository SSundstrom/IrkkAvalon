package AvalonGame;

public class Quest {
    public enum Status {
        FAILED, SUCCEEDED, AVAILABLE
    }

    private int number;

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

    public Quest(int nr, int knights, int fails) {
        number = nr;
        numberOfFails = fails;
        numberOfKnights = knights;
        status = Status.AVAILABLE;
    }

    public int getNumber() {
        return this.number;
    }

    public void setFail() {
        if (status == Status.AVAILABLE) {
            status = Status.FAILED;
        }
    }

    public void setSucceeded() {
        if (status == Status.AVAILABLE) {
            status = Status.SUCCEEDED;
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
