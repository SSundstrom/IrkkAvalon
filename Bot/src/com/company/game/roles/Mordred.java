package com.company.game.roles;

public class Mordred extends AbstractRole {
    public Mordred() {
        super("Mordred",    //Name
                true,       //see Evil
                false,      //see Merlin
                false,      //see Mordred
                false,      //seen as Evil
                false,      //seen as Merlin
                true,       //get to vote fail
                true);      //Unique
    }
}
