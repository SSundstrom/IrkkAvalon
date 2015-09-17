package com.company.game.roles;

public abstract class AbstractRole {
    private boolean seeEvil = false;
    private boolean seeMerlin = false;
    private boolean seenAsMerlin = false;
    private boolean getToShootMerlin = false;
    private boolean getToVoteFail = false;
    private boolean Uniqe = false;
    public AbstractRole() {
    }

    public AbstractRole(boolean seeEvil,
                        boolean seeMerlin,
                        boolean seenAsMerlin,
                        boolean getToShootMerlin,
                        boolean getToVoteFail,
                        boolean Uniqe) {

        this.seeEvil = seeEvil;
        this.seeMerlin = seeMerlin;
        this.seenAsMerlin = seenAsMerlin;
        this.getToShootMerlin = getToShootMerlin;
        this.getToVoteFail = getToVoteFail;
        this.Uniqe = Uniqe;
    }

    public boolean isSeeEvil() {
        return seeEvil;
    }

    public boolean isSeeMerlin() {
        return seeMerlin;
    }

    public boolean isSeenAsMerlin() {
        return seenAsMerlin;
    }

    public boolean isGetToShootMerlin() {
        return getToShootMerlin;
    }

    public boolean isGetToVoteFail() {
        return getToVoteFail;
    }

    public boolean isUniqe() {
        return Uniqe;
    }
}
