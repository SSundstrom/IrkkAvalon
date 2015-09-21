package com.company.game.roles;

public abstract class AbstractRole {
    private String name;
    private boolean seeEvil = false;
    private boolean seeMerlin = false;
    private boolean seeMordred = false;
    private boolean seenAsEvil = false;
    private boolean seenAsMerlin = false;
    private boolean getToShootMerlin = false;
    private boolean getToVoteFail = false;
    private boolean unique = false;

    public AbstractRole(String name) {
        this.name = name;
    }

    public AbstractRole(String name,
                        boolean seeEvil,
                        boolean seeMerlin,
                        boolean seeMordred,
                        boolean seenAsEvil,
                        boolean seenAsMerlin,
                        boolean getToShootMerlin,
                        boolean getToVoteFail,
                        boolean unique) {

        this.name = name;
        this.seeEvil = seeEvil;
        this.seeMerlin = seeMerlin;
        this.seeMordred = seeMordred;
        this.seenAsEvil = seenAsEvil;
        this.seenAsMerlin = seenAsMerlin;
        this.getToShootMerlin = getToShootMerlin;
        this.getToVoteFail = getToVoteFail;
        this.unique = unique;
    }

    public String getName() {
        return name;
    }

    public boolean isSeeEvil()
    {
        return seeEvil;
    }

    public boolean isSeeMerlin() {
        return seeMerlin;
    }

    public boolean isSeeMordred() {
        return seeMordred;
    }

    public boolean isSeenAsEvil() {
        return seenAsEvil;
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

    public boolean isUnique() {
        return unique;
    }
}
