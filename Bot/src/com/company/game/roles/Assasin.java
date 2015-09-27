package com.company.game.roles;

public class Assasin extends AbstractRole {
    public Assasin() {
        super("Assassin", //Name
                true,       //see Evil
                false,      //see Merlin
                true,       //see Mordred
                true,       //seen as Evil
                false,       //seen as Merlin
                true,       //get to vote fail
                true);      //Unique
    }
}
